package ch.hslu.livingWithLightColors.DataAccess;

import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogReader;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.IPropertiesAccessor;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * This class binds interfaces to concrete classes, for dependency injection.
 * @author Curdin Banzer
 */
public class DataModule extends AbstractModule{

	@Override
	protected void configure() {
		
		bind(ILogReader.class).to(RealTimeLogReader.class).in(Singleton.class);
		bind(ILoggerFactory.class).to(LoggerFactory.class).in(Singleton.class);
		bind(IPropertiesAccessor.class).to(PropertiesAccessor.class).in(Singleton.class);
	}

}
