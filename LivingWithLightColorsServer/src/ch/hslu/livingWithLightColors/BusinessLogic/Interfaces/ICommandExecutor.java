package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

/**
 * Executes commands for controlling the light.
 * @author Curdin Banzer
 */
public interface ICommandExecutor extends Runnable{

	/**
	 * Starts the executing of commands. In a separate thread.
	 */
	@Override
	void run();
	
	/**
	 * Stops the executing of commands.
	 */
	void stop();
}
