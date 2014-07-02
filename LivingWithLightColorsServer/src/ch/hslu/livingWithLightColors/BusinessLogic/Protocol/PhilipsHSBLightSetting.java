package ch.hslu.livingWithLightColors.BusinessLogic.Protocol;

import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Test.ServerModuleMock;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.Test.DataModuleMock;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.philips.lighting.model.PHLight.PHLightColorMode;
import com.philips.lighting.model.PHLightState;

/**
 * Validates and corrects RGB values and converts them into a PHLightState object. A PHLightState object is used by the philips sdk to send a light setting to a light.
 * @author Curdin Banzer
 */
public class PhilipsHSBLightSetting {
	
	private Logger logger;
	private int hue = HUE_MIN;
	private int saturation = SATURATION_MAX;
	private int brightness = BRIGHTNESS_MAX;
	private static Injector injector;
	
	private static final int BRIGHTNESS_MIN = 0;
	private static final int BRIGHTNESS_MAX = 254;
	private static final int SATURATION_MIN = 0;
	private static final int SATURATION_MAX = 254;
	private static final int HUE_MIN = 0;
	private static final int HUE_MAX = 65535;
	
	/**
	 * Constructs a LightSetting with the given values. LightSetting can just be instantiated with its values.
	 * @param hue 			the hue value(0-65535)
	 * @param saturation	the saturation value (0-254)
	 * @param brightness	the brightness value (0-254)
	 */ 
	@Inject
	public PhilipsHSBLightSetting(int hue, int saturation, int brightness){
		
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		this.injector = Guice.createInjector(new ServerModuleMock(), new DataModuleMock());
		this.logger = injector.getInstance(ILoggerFactory.class).getLogger(this.getClass());
	}
	
	/**
	 * Validates if this LightSetting is valid. Checks if hue, saturation and brightness are valid.
	 * @return true if LightSetting is valid, false if not
	 */
	public boolean validate(){
		return validateHue() && validateSaturation() && validateBrightness(); 
	}
	
	/**
	 * Validates if this LightSetting is valid. Corrects the values to valid ones, if they haven't been valid before.
	 * @return	true if LightSetting is valid, false if LightSettig was invalid and had to be corrected
	 */
	public boolean validateAndCorrect(){
		if(validate()){
			return true;
		}
		else{
			correct();
			return false;
		}
	}
	
	/**
	 * Converts this LightSetting to an PHLightState object. Corrects its values before conversion, if they are invalid.
	 * @return PHLightState object representing this LigthSetting
	 */
	public PHLightState getLightState(){
		if(!validateAndCorrect()){
			logger.warning("HSB values were corrected before conversion to PHLightState.");
		}
		
		PHLightState lightState = new PHLightState();
		lightState.setColorMode(PHLightColorMode.COLORMODE_HUE_SATURATION);
		lightState.setHue(hue);
		lightState.setSaturation(saturation);
		//lightState.setBrightness(brightness);
		
		return lightState;
	}
	
	/**
	 * Validates if the hue value of this LightSetting is valid.
	 * @return	true if valid, false if invalid
	 */
	private boolean validateHue(){
		return hue >= HUE_MIN && hue <= HUE_MAX;
	}
	
	/**
	 * Validates if the saturation value of this LightSetting is valid.
	 * @return	true if valid, false if invalid
	 */
	private boolean validateSaturation(){
		return saturation >= SATURATION_MIN && saturation <= SATURATION_MAX; 
	}
	
	/**
	 * Validates if the brightness value of this LightSetting is valid.
	 * @return	true if valid, false if invalid
	 */
	private boolean validateBrightness(){
		return brightness >= BRIGHTNESS_MIN && brightness <= BRIGHTNESS_MAX;
	}
	
	/**
	 * Corrects the values of hue, saturation and brightness, if they are invalid.
	 */
	private void correct(){
		if(!validateHue()){
			hue = HUE_MIN;
		}
		if(!validateSaturation()){
			saturation = SATURATION_MAX;
		}
		if(!validateBrightness()){
			brightness = BRIGHTNESS_MAX;
		}
	}
	
	/**
	 * Creates a String representation of an PhilipsHSBLightSetting.
	 * @return A String representing an PhilipsHSBLightSetting.
	 */
	@Override
	public String toString()
	{
		return String.format("HSB: %d %d %d", hue, saturation, brightness);
	}
}
