package ch.hslu.livingWithLightColors.BusinessLogic.Test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IRGBToPhilipsHSBConverter;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.PhilipsHSBLightSetting;
import ch.hslu.livingWithLightColors.DataAccess.Test.DataModuleMock;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RGBToPhilipsHSBConverterTest {
	
	private static Injector injector;
	private static IRGBToPhilipsHSBConverter converter;

	@Before
	public void setUp() throws Exception {
		System.out.println("RGBToPhilipsHSBConverter test is starting...");
		injector = Guice.createInjector(new ServerModuleMock(), new DataModuleMock());
		converter = injector.getInstance(IRGBToPhilipsHSBConverter.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConvertRegularValues() {
		int red = 25;
		int green = 55;
		int blue = 75;
		float[] hsb = new float[3];
		Color.RGBtoHSB(red, green, blue, hsb);
		System.out.println(String.format("HSB: %f %f %f", hsb[0], hsb[1], hsb[2]));
		PhilipsHSBLightSetting lightSetting = converter.convert(red, green, blue);
		System.out.println(lightSetting);
		assertTrue(lightSetting.validate());
	}
	
	@Test
	public void testConvertBorderValues() {
		int red = 254;
		int green = 254;
		int blue = 254;
		float[] hsb = new float[3];
		Color.RGBtoHSB(red, green, blue, hsb);
		System.out.println(String.format("HSB: %f %f %f", hsb[0], hsb[1], hsb[2]));
		PhilipsHSBLightSetting lightSetting = converter.convert(red, green, blue);
		System.out.println(lightSetting);
		assertTrue(lightSetting.validate());
	}
	
	@Test
	public void testConvertWrongValues(){
		int red = 256;
		int green = 256;
		int blue = 256;
		float[] hsb = new float[3];
		Color.RGBtoHSB(red, green, blue, hsb);
		System.out.println(String.format("HSB: %f %f %f", hsb[0], hsb[1], hsb[2]));
		PhilipsHSBLightSetting lightSetting = converter.convert(red, green, blue);
		System.out.println(lightSetting);
		assertTrue(lightSetting.validate());
	}

}
