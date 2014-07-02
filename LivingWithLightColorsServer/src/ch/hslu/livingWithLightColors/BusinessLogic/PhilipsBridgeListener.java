package ch.hslu.livingWithLightColors.BusinessLogic;

import java.util.List;
import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IBridge;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IBridgeListener;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.IPropertiesAccessor;

import com.google.inject.Inject;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;

/**
 * Listens to bridge messages and state changes.
 * Specific implementation for Philips Hue
 * @author Stefan
 *
 */
public class PhilipsBridgeListener implements IBridgeListener {

	private Logger logger;
	
	private PHHueSDK sdk;
	private IBridge bridge;
	private IPropertiesAccessor propertiesAccessor;

	@Inject
	public PhilipsBridgeListener(ILoggerFactory loggerFactory, IBridge bridge, IPropertiesAccessor propertiesAccessor) {
		this.logger = loggerFactory.getLogger(this.getClass());
		this.bridge = bridge;
		this.propertiesAccessor = propertiesAccessor;
	}
	
	@Override
	public void init() {
		sdk = PHHueSDK.getInstance();
	}

	@Override
	public void onAccessPointsFound(List<PHAccessPoint> accessPointList) {
		try{
			if(accessPointList != null && accessPointList.size() > 0 ){
				PHAccessPoint accessPoint = accessPointList.get(0);
				if(accessPoint != null && !bridge.isConnected()){
					logger.info("Access point found. IP: "+accessPoint.getIpAddress());	
					accessPoint.setUsername(propertiesAccessor.getUsername());
					sdk.connect(accessPoint);
				}
			}
		}
		catch(Exception e){
			logger.warning("Failure when accessPoint was found: "+e.getMessage());
		}
	}

	@Override
	public void onAuthenticationRequired(PHAccessPoint accessPoint) {
		sdk.startPushlinkAuthentication(accessPoint);
		logger.info("Starting pushlink authentication.");
	}

	@Override
	public void onBridgeConnected(PHBridge newBridge) {
		sdk.setSelectedBridge(newBridge);
		sdk.enableHeartbeat(newBridge, PHHueSDK.HB_INTERVAL);
		bridge.setConnectionState(true);
		logger.info("Bridge connected. Lights can now be controlled.");
		saveBridgeIpAddressToPropertiesFile(newBridge);
	}

	private void saveBridgeIpAddressToPropertiesFile(PHBridge newBridge) {
		try{
			String ipAddress = newBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
			propertiesAccessor.setIpAddress(ipAddress);
		}
		catch(Exception e){
			logger.warning("Failure when trying to save bridge ip to properties file: "+e.getMessage());
		}
	}

	@Override
	public void onCacheUpdated(int arg0, PHBridge arg1) {
		logger.fine("Cache updated.");
	}

	@Override
	public void onConnectionLost(PHAccessPoint arg0) {
		logger.info("Connection lost.");
		bridge.setConnectionState(false);
	}

	@Override
	public void onConnectionResumed(PHBridge arg0) {
		logger.fine("Connection resumed.");
	}

	@Override
	public void onError(int code, final String message) {
		logger.warning("ERROR");
		if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
			logger.warning("ERROR: Bridge ist not responding.");
		} else if (code == PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED) {
			logger.warning("ERROR: Pushlink button not pressed.");
		} else if (code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
			logger.warning("ERROR: Pushlink authentication failed.");
		} else if (code == PHMessageType.BRIDGE_NOT_FOUND) {
			logger.warning("ERROR: Bridge not found.");
		}
	}
}