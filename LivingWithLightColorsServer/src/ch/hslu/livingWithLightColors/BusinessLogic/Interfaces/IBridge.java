package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

/**
 * Manages the connection to the lamp bridge. Opens and closes it, as wished.
 * @author Curdin Banzer
 *
 */
public interface IBridge {

	/**
	 * Opens the connection to the lamp bridge. So that communication with the bridge and control of the lamps is possible.
	 */
	void connect();
	
	/**
	 * Closes the connection to the lamp bridge. So that communication to the bridge and control of the lamps ends.
	 */
	void disconnect();

	/**
	 * Sets the connection state to the bridge.
	 * @param connectionEstablished	The current connection state, to be set.
	 */
	void setConnectionState(boolean connectionEstablished);
	
	/**
	 * Checks if the bridge is connected and available. If not, it tries to connect to the bridge.
	 * @return	True if the bridge is available, false if not.
	 */
	public boolean isConnected();
}
