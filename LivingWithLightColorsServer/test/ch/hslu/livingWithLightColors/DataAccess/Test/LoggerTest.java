package ch.hslu.livingWithLightColors.DataAccess.Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.Test.ServerModuleMock;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class LoggerTest {
	
	private static Injector injector;
	private static ILoggerFactory loggerFactory;

	@Before
	public void setUp() throws Exception {
		System.out.println("Logger test is starting...");
		injector = Guice.createInjector(new ServerModuleMock(), new DataModuleMock());
		loggerFactory = injector.getInstance(ILoggerFactory.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLogger() {
		assertNotNull(loggerFactory.getLogger(this.getClass()));
	}

}
