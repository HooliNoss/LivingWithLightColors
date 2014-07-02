package ch.hslu.livingWithLightColors.DataAccess.Abstraction;

/**
 * LogListeners can be registered to the LogReader, which informs them when new messages are received.
 * @author Curdin Banzer
 *
 */
public interface ILogListener {
	
	/**
	 * Is called by the LogReader, when new messages are received.
	 * @param message The message, which was received by the LogReader.
	 */
	void newMessageReceived(String message);

}
