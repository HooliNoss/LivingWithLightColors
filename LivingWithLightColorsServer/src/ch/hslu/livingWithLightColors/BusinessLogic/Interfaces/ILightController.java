package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.PhilipsHSBLightSetting;

/**
 * Controls the lights.
 * @author Curdin Banzer
 *
 */
public interface ILightController {

	/**
	 * Prepares the light controller. After that the lights can be controlled.
	 */
	void open();
	/**
	 * Closes the light controller. After that, no lights can be controlled any more.
	 */
	void close();
	/**
	 * Changes the color of all lights.
	 * @param settings	The settings, which the lights should overtake.
	 */
	void setColor(PhilipsHSBLightSetting settings);
	
	/**
	 * Changes the brightness of all lights.
	 * @param brightness The brightness, which the lights should overtake.
	 */
	void setBrightness(int brightness);
	
	/**
	 * Turns all lights on.
	 */
	void turnOn();
	/**
	 * Turns all lights off.
	 */
	void turnOff();
	/**
	 * Checks, if the connection to the bridge is established.
	 * @return	true if connection established, false if not.
	 */
	boolean isBridgeConnected();

}
