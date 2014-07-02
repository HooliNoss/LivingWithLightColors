package ch.hslu.livingWithLightColors.BusinessLogic;

import java.util.List;
import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IBridge;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IBridgeListener;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.IPropertiesAccessor;

import com.google.inject.Inject;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHLight;

/**
 * Manages the connection to the lamp bridge. Opens and closes it, as wished.
 * Specific implementation for Philips Hue
 * @author Stefan
 *
 */
public class PhilipsBridge implements IBridge {

	private static final int MANUAL_CONNECTION_LATENCY_MS = 1000;
	private static final int MANUAL_CONNECTION_ATTEMPTS = 3;
	private IBridgeListener bridgeListener;
	private IPropertiesAccessor propertiesAccessor;
	private Logger logger;
	private PHHueSDK sdk;
	private static boolean initialized = false;
	private static boolean connectionEstablished = false;
	private static boolean lightsFound = false;
	
	@Inject
	public PhilipsBridge(IBridgeListener listener, IPropertiesAccessor accessor, ILoggerFactory loggerFactory){
		this.bridgeListener = listener;
		this.propertiesAccessor = accessor;
		this.logger = loggerFactory.getLogger(this.getClass());
	}

	@Override
	public void connect() {
		
		init();
		connectManually();
		
		if(!connectionEstablished){
			connectAutomatically();
		}
	}
	
	private void init(){
		if(!initialized){
			bridgeListener.init();
			sdk = PHHueSDK.getInstance();
			sdk.setDeviceName(propertiesAccessor.getDeviceName());
			sdk.getNotificationManager().registerSDKListener(bridgeListener);
			initialized = true;
			logger.fine("Bridge initialized.");
		}
		else{
			logger.info("Bridge already initialized. Skipped.");
		}
	}
	
	private void connectAutomatically() {
		logger.info("Trying to connect automatically.");
		PHBridgeSearchManager searchManager = (PHBridgeSearchManager) sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE);
		searchManager.search(true, true);
	}

	private void connectManually(){
		PHAccessPoint accessPoint = new PHAccessPoint();
		accessPoint.setIpAddress(propertiesAccessor.getConnectionString());
		accessPoint.setUsername(propertiesAccessor.getUsername());
		logger.info(String.format("Connection String: %s | Username: %s", propertiesAccessor.getConnectionString(), propertiesAccessor.getUsername()));
		sdk.connect(accessPoint);
		
		for(int i = 0; i < MANUAL_CONNECTION_ATTEMPTS; i++){
			if(!connectionEstablished){
				try {
					Thread.sleep(MANUAL_CONNECTION_LATENCY_MS);
				} catch (InterruptedException e) {
					logger.warning(e.getMessage());
				}
			}
			else{
				logger.info("Connection established manually.");
				return;
			}
		}
		logger.info("Connection could not be established manually.");
	}
	
	@Override
	public void disconnect(){
		if(sdk != null){
			sdk.disableAllHeartbeat();
			if(sdk.getSelectedBridge() != null){
				sdk.disconnect(sdk.getSelectedBridge());
			}
		}
		else{
			logger.warning("Tried to close BridgeConnection before opening it.");
		}
		connectionEstablished = false;
	}
	
	@Override
	public void setConnectionState(boolean connectionEstablished){
		this.connectionEstablished = connectionEstablished;
		logger.info("Connection state changed to "+connectionEstablished+".");
		if(connectionEstablished && lightsFound == false){
			searchLights();
		}
		else if(!connectionEstablished){
			lightsFound = false;
			// Trying to reconnect, when connection lost.
			connect();
		}
	}
	

	@Override
	public boolean isConnected() {
		if(!initialized){
			logger.warning("Bridge connection is not prepared yet.");
			return false;
		}
		else if (!connectionEstablished) {
			logger.warning("Bridge is not connected yet.");
			return false;
		}else if(sdk.getSelectedBridge() == null){
			logger.warning("Selected bridge is null.");
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Starts a search after new light bulbs asynchronously. The search takes 60 seconds to process. 
	 */
	private void searchLights(){
		List<PHLight> lights = sdk.getSelectedBridge().getResourceCache().getAllLights();
		if(lights == null || lights.isEmpty()){
			logger.info("Starting search for light bulbs. Please wait 60 seconds.");
			sdk.getSelectedBridge().findNewLights(null);
		}
		lightsFound = true;
	}
}
