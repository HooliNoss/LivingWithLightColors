package ch.hslu.livingWithLightColors.DataAccess.Abstraction;

/**
 * Interface for a properties accessor. They read and write properties from/to properties file.
 * @author Curdin Banzer
 *
 */
public interface IPropertiesAccessor {

	/**
	 * Reads the properties from a properties file. If not happened yet.
	 */
	void loadProperties();
	/**
	 * Returns the username for connection to the bridge.
	 * @return	The username, for connection to the bridge.
	 */
	String getUsername();
	/**
	 * Returns the ip address of the bridge, which is needed for establishing an connection.
	 * @return	The ip adress of the bridge.
	 */
	String getIpAddress();
	/**
	 * Saves the ip address of the bridge to the properties file.
	 * @param ipAddress The ip address of the bridge.
	 */
	void setIpAddress(String ipAddress);
	/**
	 * Returns the port on the bridge, to which the connection should be established.
	 * @return	The port on the bridge.
	 */
	String getPort();
	/**
	 * Returns the connection string, used to establish a connection to the bridge.
	 * @return	The connection string to the bridge.
	 */
	String getConnectionString();
	/**
	 * Returns the path to the directory, in which the philips sdk is placed.
	 * @return	The path to the sdk directory.
	 */
	String getSdkDir();
	/**
	 * Returns the path to be defined.
	 * @return	The path to be defined.
	 */
	String getPath();
	/**
	 * Returns the path to the project folder.
	 * @return	The Path to the project folder.
	 */
	String getProjectFolder();
	/**
	 * Returns the start command for the sifteo app.
	 * @return	The command for starting the sifteo app.
	 */
	String getStartCmd();
	
	/**
	 * Returns the kill command for the sifteo app.
	 * @return	The command for killing the sifteo app.
	 */
	String getKillCmd();
	
	/**
	 * Returns the device name.
	 * @return	The device name.
	 */
	String getDeviceName();
}
