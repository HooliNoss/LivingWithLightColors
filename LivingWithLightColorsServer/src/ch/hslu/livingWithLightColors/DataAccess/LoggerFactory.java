package ch.hslu.livingWithLightColors.DataAccess;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

public class LoggerFactory implements ILoggerFactory {
	
	private static Level logLevel = Level.ALL;
	
	@Override
	public Logger getLogger(Class<?> owner){
		Logger logger = Logger.getLogger(owner.getSimpleName());
		logger.setLevel(logLevel);
		ConsoleHandler handler = new ConsoleHandler();
		logger.setUseParentHandlers(false);
		logger.addHandler(handler);
		handler.setFormatter(new ConsoleFormatter());
		return logger;	
	}
	
	@Override
	public void setLogLevel(Level level){
		logLevel = level;
	}

}
