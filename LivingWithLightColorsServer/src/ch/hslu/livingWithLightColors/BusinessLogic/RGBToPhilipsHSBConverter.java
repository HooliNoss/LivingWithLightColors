package ch.hslu.livingWithLightColors.BusinessLogic;

import java.awt.Color;
import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IRGBToPhilipsHSBConverter;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.PhilipsHSBLightSetting;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Inject;

/**
 * This class converts RGB values to a PhilipsHSBLightSetting.
 * Specific implementation for Philips Hue
 * @author Stefan
 *
 */
public class RGBToPhilipsHSBConverter implements IRGBToPhilipsHSBConverter {
	
	private Logger logger;
	
	private static final int HUE_MAX = 65535;
	private static final int SATURATION_MAX = 254;
	private static final int BRIGHTNESS_MAX = 254;
	private static final int BLUE_BEGIN = 34000;
	private static final int BLUE_END = 47000;
	private static final int J_HUE = 90;
	private static final float K_HUE = 0.2f;
	private static final float K_SATURATION = 0.4f;
	private static final float K_BRIGHTNESS = 0.7f;
	
	@Inject
	public RGBToPhilipsHSBConverter(ILoggerFactory loggerFactory)
	{
		this.logger = loggerFactory.getLogger(this.getClass());
	}

	@Override
	public PhilipsHSBLightSetting convert(int red, int green, int blue) {
				
		float[] hsb = new float[3];
		Color.RGBtoHSB(red, green, blue, hsb);
		
		int hue = (int) (hsb[0] * HUE_MAX);
		int saturation = (int) (hsb[1] * SATURATION_MAX);
		int brightness = (int) (hsb[2] * BRIGHTNESS_MAX);
		
		int[] hsb_blue = makeBlueMoreVivid(hue, saturation, brightness);
		hue = hsb_blue[0];
		saturation = hsb_blue[1];
		brightness = hsb_blue[2];
			
		logger.info(String.format("Hue: %d", hue));
		logger.info(String.format("Saturation: %d", saturation));
		logger.info(String.format("Brightness: %d", brightness));
		
		PhilipsHSBLightSetting setting = new PhilipsHSBLightSetting(hue, saturation, brightness);
		if (!setting.validateAndCorrect())
		{
			logger.warning(setting.toString());
		}
		
		return setting;
	}
	
	private int[] makeBlueMoreVivid(int hue, int saturation, int brightness){
		
		if(hue > BLUE_BEGIN && hue < BLUE_END ){
			hue = Math.min((int)(hue + (hue *K_HUE) + J_HUE), BLUE_END);
			saturation = Math.min((int)(saturation + (saturation * K_SATURATION)), SATURATION_MAX);
			brightness = Math.min((int)(brightness - (brightness * K_BRIGHTNESS)), BRIGHTNESS_MAX);
		}
		return new int[]{hue, saturation, brightness};
	}
	
	

}
