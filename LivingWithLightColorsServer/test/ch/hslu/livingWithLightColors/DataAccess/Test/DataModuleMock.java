package ch.hslu.livingWithLightColors.DataAccess.Test;

import ch.hslu.livingWithLightColors.DataAccess.LoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.PropertiesAccessor;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogReader;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.IPropertiesAccessor;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * This class binds interfaces to concrete classes, for dependency injection. It also loads some mocking classes instead of the "real classes", for testing reasons.
 * @author Curdin Banzer
 */
public class DataModuleMock extends AbstractModule{

	@Override
	protected void configure() {
		
		bind(ILogReader.class).to(RealTimeLogReaderMock.class).in(Singleton.class);
		bind(ILoggerFactory.class).to(LoggerFactory.class).in(Singleton.class);
		bind(IPropertiesAccessor.class).to(PropertiesAccessor.class).in(Singleton.class);
	}

}
