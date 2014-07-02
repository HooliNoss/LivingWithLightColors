package ch.hslu.livingWithLightColors.Start;

import ch.hslu.livingWithLightColors.BusinessLogic.ServerModule;
import ch.hslu.livingWithLightColors.BusinessLogic.Abstraction.ILightColorsServer;
import ch.hslu.livingWithLightColors.DataAccess.DataModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This ist the main class. It starts the server and adds an shutdownhook.
 * @author Curdin Banzer
 *
 */
public class Main {

	public static Injector injector;
	
	public static void main(String[] args) {
		
		System.out.println("Server is starting...");
		
		injector = Guice.createInjector(new ServerModule(), new DataModule());
		final ILightColorsServer server = injector.getInstance(ILightColorsServer.class);
		
        Runtime.getRuntime().addShutdownHook(new Thread() {
        	@Override
        	public void run() {
                System.out.println("Shutdown Hook is running !");
                server.stop();
            }
        });
		server.run();
	}

}
