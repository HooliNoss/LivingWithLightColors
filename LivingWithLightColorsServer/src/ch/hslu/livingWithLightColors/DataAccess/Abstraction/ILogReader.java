package ch.hslu.livingWithLightColors.DataAccess.Abstraction;

/**
 * Interface for a log reader. Log readers read log messages and inform their listeners.
 * @author Curdin Banzer
 *
 */
public interface ILogReader extends Runnable{
	
	/**
	 * Starts the log reader.
	 */
	@Override
	void run();
	
	/**
	 * Stops the log reader.
	 */
	void stop();
	
	/**
	 * Adds a log listener. Log listeners are informed when new messages are read.
	 * @param listener The listener to register.
	 */
	void addLogListener(ILogListener listener);
	
	/**
	 * Removes a log listener. So that it gets not informed any more when new messages are red.
	 * @param listener The listener to unregister.
	 */
	void removeLogListener(ILogListener listener);
}
