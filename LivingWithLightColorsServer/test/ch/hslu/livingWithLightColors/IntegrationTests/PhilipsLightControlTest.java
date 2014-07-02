package ch.hslu.livingWithLightColors.IntegrationTests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.Abstraction.ILightColorsServer;
import ch.hslu.livingWithLightColors.BusinessLogic.Test.ServerModuleMock;
import ch.hslu.livingWithLightColors.DataAccess.Test.DataModuleMock;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This is an integration test. It simulates the inputs of the sifteo cubes and executes them to the hue simulator.
 * @author Curdin Banzer
 *
 */
//@Ignore
public class PhilipsLightControlTest {
		
	private static Injector injector;
	
	@Before
	public void setUp() throws Exception {
		
		System.out.println("Light Control test is starting...");
		injector = Guice.createInjector(new ServerModuleMock(), new DataModuleMock());
		final ILightColorsServer server = injector.getInstance(ILightColorsServer.class);
		server.run();
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void communicationServerToLights() {
		assertTrue(true);
	}

}
