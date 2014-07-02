package ch.hslu.livingWithLightColors.BusinessLogic.Abstraction;

/**
 * The LightColorsServer is the central steering part of the application. It
 * controls the LogReader for command listening and the CommandExecutor for
 * executing them. Beside that, it opens and closes the connection to the
 * bridge.
 * @author Curdin Banzer
 */
public interface ILightColorsServer {
	
	/**
	 * Runs the server application. Should only be called once directly after starting the program. Prepares the BridgeConnection, starts the LogReader and the CommandExecutor.
	 */
	void run();
	
	/**
	 * Stops the server application. Should only be called before terminating the program.
	 */
	void stop();
}
