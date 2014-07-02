package ch.hslu.livingWithLightColors.BusinessLogic;

import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IBridge;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ILightController;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.PhilipsHSBLightSetting;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Inject;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHLightState;

/**
 * Controls the lights. 
 * Specific implementation for Philips Hue
 * @author Stefan
 *
 */
public class PhilipsLightController implements ILightController {

	private Logger logger;
	private PHHueSDK sdk;
	private IBridge bridge;
	private static final int DEFAULT_GROUP = 0;
	private static final int MAX_BRIGHTNESS = 254;
	private static final int MIN_BRIGHTNESS = 0;

	@Inject
	public PhilipsLightController(ILoggerFactory loggerFactory, IBridge bridge) {
		this.logger = loggerFactory.getLogger(this.getClass());
		this.bridge = bridge;
	}

	@Override
	public void open() {
		// Creates an philips sdk object
		sdk = PHHueSDK.create();
		// Opens a connection to the bridge
		bridge.connect();
	}

	@Override
	public void close() {
		// Closes the connection to the bridge
		bridge.disconnect();
		// Destroys the philips sdk object
		sdk.destroySDK();
	}

	@Override
	public void turnOn() {
		turnOnOff(true);
	}

	@Override
	public void turnOff() {
		turnOnOff(false);
	}

	private void turnOnOff(boolean on) {
		// If bridge and lights available
		if (bridge.isConnected()) {
			// Create new PHLightState an set light state to on or off
			PHLightState lightState = new PHLightState();
			lightState.setOn(on);

			// Update light group state
			sdk.getSelectedBridge().setLightStateForGroup("" + DEFAULT_GROUP,
					lightState, null);
			logger.info("Turned lights on/off.");
		} else {
			logger.warning("Bridge not available.");
		}
	}

	@Override
	public void setColor(PhilipsHSBLightSetting settings) {
		// If bridge and lights available
		if (bridge.isConnected()) {
			// Validate values and correct if necessary
			if (!settings.validateAndCorrect()) {
				logger.warning("LightSetting value has been invalid and was set to its default value.");
			}

			// Get PHLightState representing the LightSettings
			PHLightState lightState = settings.getLightState();

			// Update group light state
			sdk.getSelectedBridge().setLightStateForGroup(""+DEFAULT_GROUP, lightState, null);
			logger.info("Light color changed.");
		} else {
			logger.warning("Bridge not available.");
		}
	}
	
	@Override
	public void setBrightness(int brightness){
		if(brightness < MIN_BRIGHTNESS || brightness > MAX_BRIGHTNESS){
			brightness = MIN_BRIGHTNESS;
		}
		PHLightState lightState = new PHLightState();
		lightState.setBrightness(brightness);
		sdk.getSelectedBridge().setLightStateForGroup(""+DEFAULT_GROUP, lightState, null);
		logger.info(String.format("Set brightness of lights to %d.", brightness));
	}
	
	@Override
	public boolean isBridgeConnected(){
		return bridge.isConnected();
	}
}
