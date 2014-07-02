package ch.hslu.livingWithLightColors.BusinessLogic.Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandQueue;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewBrightnessCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewColorCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbOffCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbOnCommand;
import ch.hslu.livingWithLightColors.DataAccess.Test.DataModuleMock;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CommandQueueTest {

	private static Injector injector;
	private static ICommandQueue queue;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("CommandQueue test is starting...");
		injector = Guice.createInjector(new ServerModuleMock(), new DataModuleMock());
		queue = injector.getInstance(ICommandQueue.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOverflow() {
		BaseCommand briCmd = new LightBulbNewBrightnessCommand();
		BaseCommand colCmd = new LightBulbNewColorCommand();
		BaseCommand offCmd = new LightBulbOffCommand();
		BaseCommand onCmd = new LightBulbOnCommand();
				
		queue.putCommand(briCmd); // is overwritten by followers
		queue.putCommand(colCmd); // is queued
		queue.putCommand(offCmd); // is queued
		queue.putCommand(onCmd);  // is queued
		
		assertSame(colCmd, queue.getCommand());
		assertSame(offCmd, queue.getCommand());
		assertSame(onCmd, queue.getCommand());
		
	}

}
