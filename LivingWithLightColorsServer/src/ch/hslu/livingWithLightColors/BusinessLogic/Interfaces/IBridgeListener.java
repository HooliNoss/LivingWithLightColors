package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

import com.philips.lighting.hue.sdk.PHSDKListener;

/**
 * Listens to bridge messages and state changes.
 * @author Curdin Banzer
 */
public interface IBridgeListener extends PHSDKListener {

	/**
	 * Initializes the BridgeListener. Has to be called, before registering the
	 * listener.
	 */
	void init();
}
