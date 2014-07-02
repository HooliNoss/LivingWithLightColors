package ch.hslu.livingWithLightColors.DataAccess;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.google.inject.Inject;

import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.IPropertiesAccessor;

/**
 * Reads and writes the internal properties file
 * @author Stefan
 *
 */
public class PropertiesAccessor implements IPropertiesAccessor {
	
	private Logger logger;
	private static final String PROPERTIES_FILE_NAME = "properties.conf";
	private static final String PROPERTIES_USERNAME = "USERNAME";
	private static final String PROPERTIES_DEVICENAME = "DEVICENAME";
	private static final String PROPERTIES_IPADDRESS = "IPADDRESS";
	private static final String PROPERTIES_PORT = "PORT";
	private static final String PROPERTIES_SDKDIR = "SDKDIR";
	private static final String PROPERTIES_PATH = "PATH";
	private static final String PROPERTIES_STARTCMD = "STARTCMD";
	private static final String PROPERTIES_PROJECTFOLDER = "PROJECTFOLDER";
	private static final String PROPERTIES_KILLCMD = "KILLCMD";
	private static Properties properties;
	private static String ipAddress;
	private static String port;
	private static String username;
	private static String deviceName;
	private static String sdkDir;
	private static String path;
	private static String startCmd;
	private static String projectFolder;
	private static String killCmd;

	@Inject
	public PropertiesAccessor(ILoggerFactory loggerFactory){
		this.logger = loggerFactory.getLogger(this.getClass());
		loadProperties();
	}
	

	@Override
	public void loadProperties() {
		if (properties == null) {
			properties = new Properties();
		}

		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(PROPERTIES_FILE_NAME);
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			System.out.println("Properties file could not be accessed. Trying to create it.");
			properties = generateDefaultPropertiesFile();
			savePropertiesFile(properties);
		}
		
		username = properties.getProperty(PROPERTIES_USERNAME);
		deviceName = properties.getProperty(PROPERTIES_DEVICENAME);
		ipAddress = properties.getProperty(PROPERTIES_IPADDRESS);
		port = properties.getProperty(PROPERTIES_PORT);
		sdkDir = properties.getProperty(PROPERTIES_SDKDIR);
		path = properties.getProperty(PROPERTIES_PATH);
		startCmd = properties.getProperty(PROPERTIES_STARTCMD);
		projectFolder = properties.getProperty(PROPERTIES_PROJECTFOLDER);
		killCmd = properties.getProperty(PROPERTIES_KILLCMD);
		
	}

	/**
	 * Generates a properties file containing the default values.
	 * @return	A properties file with default values.
	 */
	private Properties generateDefaultPropertiesFile() {
		Properties defaultProperties = new Properties();
		defaultProperties.setProperty(PROPERTIES_USERNAME, "SecretUser389");
		defaultProperties.setProperty(PROPERTIES_DEVICENAME, "LivingWithLightColors");
		defaultProperties.setProperty(PROPERTIES_IPADDRESS, "127.0.0.1");
		defaultProperties.setProperty(PROPERTIES_PORT, "80");
		defaultProperties.setProperty(PROPERTIES_SDKDIR, "C:\\BDA\\Sifteo");
		defaultProperties.setProperty(PROPERTIES_PATH, "%SDK_DIR%\\bin;%PATH%");
		defaultProperties.setProperty(PROPERTIES_STARTCMD, "swiss listen CubeController.elf");
		defaultProperties.setProperty(PROPERTIES_PROJECTFOLDER, "C:\\BDA\\BDAStefanCurdin\\LivingWithLightColors\\LivingWithLightColors");
		defaultProperties.setProperty(PROPERTIES_KILLCMD, "cmd /c taskkill /IM swiss.exe /F");
		return defaultProperties;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getDeviceName() {
		return deviceName;
	}

	@Override
	public String getIpAddress() {
		return ipAddress;
	}
	
	@Override
	public void setIpAddress(String ipAddress){
		if(!ipAddress.equals(this.ipAddress)){
			logger.info("Writing bridge ip address.");
			this.ipAddress = ipAddress;
			properties.setProperty(PROPERTIES_IPADDRESS, ipAddress);
			savePropertiesFile(properties);
		}
	}

	@Override
	public String getPort() {
		return port;
	}

	@Override
	public String getConnectionString() {
		return ipAddress + ":" + port;
	}
	
	@Override
	public String getSdkDir(){
		return sdkDir;
	}
	
	@Override
	public String getPath(){
		return path;
	}
	
	@Override
	public String getStartCmd(){
		return startCmd;
	}
	
	@Override
	public String getProjectFolder(){
		return projectFolder;
	}
	
	@Override
	public String getKillCmd(){
		return killCmd;
	}
	
	/**
	 * Saves the properties file to harddisk.
	 * @param defaultProperties The properties to store.
	 */
	private void savePropertiesFile(Properties defaultProperties){
		try{
			FileOutputStream outputStream = new FileOutputStream(PROPERTIES_FILE_NAME);
	        defaultProperties.store(outputStream, null);
	        outputStream.close();
	        logger.info("Properties file created or updated successfully.");
		}
		catch(IOException e){
			logger.warning("Properties file could not be created: "+e.getMessage());
		}
	}

}
