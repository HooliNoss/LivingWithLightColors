package ch.hslu.livingWithLightColors.DataAccess.Test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.Test.ServerModuleMock;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.IPropertiesAccessor;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PropertiesAccessorTest {
	
	private static Injector injector;
	private static IPropertiesAccessor propertiesAccessor;
	private static String deviceName = "LivingWithLightColors";
	private static String username = "SecretUser389";
	private static String ipAddress = "127.0.0.1";
	private static String port = "80";
	private static String connectionString = ipAddress +":"+ port;
	private static String sdkDir = "C:\\BDA\\Sifteo";
	private static String path = "%SDK_DIR%\\bin;%PATH%";
	private static String startCmd = "swiss listen CubeController.elf";
	private static String projectFolder = "C:\\BDA\\BDAStefanCurdin\\LivingWithLightColors\\LivingWithLightColors";
	private static String killCmd = "cmd /c taskkill /IM swiss.exe /F";

	@Before
	public void setUp() throws Exception {
		System.out.println("PropertiesAccessor test is starting...");
		injector = Guice.createInjector(new ServerModuleMock(), new DataModuleMock());
		propertiesAccessor = injector.getInstance(IPropertiesAccessor.class);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testWriteAndReadIpAddress(){
		propertiesAccessor.setIpAddress(ipAddress);
		propertiesAccessor.loadProperties();
		assertTrue(propertiesAccessor.getIpAddress().equals(ipAddress));
	}
	
	@Test
	public void testReadProperties() {
		propertiesAccessor.loadProperties();
		assertTrue(propertiesAccessor.getDeviceName().equals(deviceName));
		assertTrue(propertiesAccessor.getUsername().equals(username));
		System.out.println(propertiesAccessor.getIpAddress());
		assertTrue(propertiesAccessor.getIpAddress().equals(ipAddress));
		assertTrue(propertiesAccessor.getPort().equals(port));
		assertTrue(propertiesAccessor.getConnectionString().equals(connectionString));
		assertTrue(propertiesAccessor.getSdkDir().equals(sdkDir));
		assertTrue(propertiesAccessor.getPath().equals(path));
		assertTrue(propertiesAccessor.getStartCmd().equals(startCmd));
		assertTrue(propertiesAccessor.getProjectFolder().equals(projectFolder));
		assertTrue(propertiesAccessor.getKillCmd().equals(killCmd));
	}

}
