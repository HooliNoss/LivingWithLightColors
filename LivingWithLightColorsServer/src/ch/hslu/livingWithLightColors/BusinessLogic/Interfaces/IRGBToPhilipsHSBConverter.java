package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.PhilipsHSBLightSetting;

/**
 * This class converts RGB values to a PhilipsHSBLightSetting.
 * @author Curdin Banzer
 *
 */
public interface IRGBToPhilipsHSBConverter {

	/**
	 * Converts RGB values to a PhilipsHSBLightSetting.
	 * @param red	red value (0-254)
	 * @param green	green value (0-254)
	 * @param blue	blue value (0-254)
	 * @return		An Object (PhilipsHSBLightSetting), representing a light setting.
	 */
	PhilipsHSBLightSetting convert(int red, int green, int blue);
}
