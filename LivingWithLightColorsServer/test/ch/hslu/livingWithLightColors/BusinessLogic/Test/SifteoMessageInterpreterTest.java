package ch.hslu.livingWithLightColors.BusinessLogic.Test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IMessageInterpreter;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.Commands;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewBrightnessCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewColorCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbOffCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbOnCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.UnknownCommand;
import ch.hslu.livingWithLightColors.DataAccess.Test.DataModuleMock;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class SifteoMessageInterpreterTest {
	
	private static Injector injector;
	private static IMessageInterpreter interpreter;
	private static String prefix = "#COMMAND:";
	private static String suffix = ":\n";
	private static Random random;
	

	@Before
	public void setUp() throws Exception {
		System.out.println("SifteoMessageInterpreter test is starting...");
		injector = Guice.createInjector(new ServerModuleMock(), new DataModuleMock());
		interpreter = injector.getInstance(IMessageInterpreter.class);
		random = new Random();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLightBulbOnCommandInterpretation() {
		BaseCommand command = interpreter.interpreteMessage(formatCommand(Commands.LightBulbOn,null));
		assertTrue(command instanceof LightBulbOnCommand);
	}
	
	@Test
	public void testLightBulbOffCommandInterpretation() {
		BaseCommand command = interpreter.interpreteMessage(formatCommand(Commands.LightBulbOff,null));
		assertTrue(command instanceof LightBulbOffCommand);
	}
	
	@Test
	public void testLightBulbNewColorCommandInterpretation() {
		BaseCommand command = interpreter.interpreteMessage(formatCommand(Commands.LightBulbNewColor,String.format(":%d:%d:%d:",
					random.nextInt(255), random.nextInt(255),
					random.nextInt(255))));
		assertTrue(command instanceof LightBulbNewColorCommand);
	}
	
	@Test
	public void testLightBulbNewBrightnessCommandInterpretation() {
		BaseCommand command = interpreter.interpreteMessage(formatCommand(Commands.LightBulbNewBrightness,String.format(":%d:",
				random.nextInt(255))));
		assertTrue(command instanceof LightBulbNewBrightnessCommand);
	}
	
	@Test
	public void testInvalidCommandInterpretation1() {
		BaseCommand command = interpreter.interpreteMessage("GUGUS");
		assertTrue(command instanceof UnknownCommand);
	}
	
	@Test
	public void testInvalidCommandInterpretation2() {
		BaseCommand command = interpreter.interpreteMessage(prefix+"GUGUS");
		assertTrue(command instanceof UnknownCommand);
	}
	
	private String formatCommand(Commands command, String supplement){
		if(supplement == null){
			supplement ="";
		}
			return prefix+command.toString()+supplement+suffix;
	}

}
