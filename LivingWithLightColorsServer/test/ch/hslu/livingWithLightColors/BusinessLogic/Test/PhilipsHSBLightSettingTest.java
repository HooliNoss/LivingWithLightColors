package ch.hslu.livingWithLightColors.BusinessLogic.Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.PhilipsHSBLightSetting;

import com.philips.lighting.model.PHLightState;

public class PhilipsHSBLightSettingTest {

	@Before
	public void setUp() throws Exception {
		System.out.println("PhilipsHSBLightSetting test is starting...");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidateValidLightSetting() {
		int hue = 40000;
		int saturation = 200;
		int brightness = 150;
		PhilipsHSBLightSetting lightSetting = new PhilipsHSBLightSetting(hue, saturation, brightness);
		assertTrue(lightSetting.validate());
		PHLightState lightState = lightSetting.getLightState();
		assertNull(lightState.validateState());
	}
	
	@Test
	public void testValidateInvalidLightSetting() {
		int hue = 70000;
		int saturation = 200;
		int brightness = 150;
		PhilipsHSBLightSetting lightSetting = new PhilipsHSBLightSetting(hue, saturation, brightness);
		assertFalse(lightSetting.validate());
		PHLightState lightState = lightSetting.getLightState();
		assertNull(lightState.validateState());
	}
	
	@Test
	public void testValidateAndCorrectLightSetting() {
		int hue = 40000;
		int saturation = 255;
		int brightness = 150;
		PhilipsHSBLightSetting lightSetting = new PhilipsHSBLightSetting(hue, saturation, brightness);
		assertFalse(lightSetting.validateAndCorrect());
		PHLightState lightState = lightSetting.getLightState();
		assertNull(lightState.validateState());
	}

}
