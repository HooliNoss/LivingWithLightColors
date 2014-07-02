package ch.hslu.livingWithLightColors.DataAccess.Abstraction;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is an interface for logger factories. By setting the log level, log messages can be filtered globally.
 * @author Curdin Banzer
 *
 */
public interface ILoggerFactory {

	/**
	 * Sets the log level. Log messages with a less important level are filtered.
	 * @param level
	 */
	void setLogLevel(Level level);
	/**
	 * Returns a logger instance for the given class.
	 * @param owner The class, that orders the logger instance.
	 * @return	A logger instance for the requesting class.
	 */
	Logger getLogger(Class<?> owner);

}