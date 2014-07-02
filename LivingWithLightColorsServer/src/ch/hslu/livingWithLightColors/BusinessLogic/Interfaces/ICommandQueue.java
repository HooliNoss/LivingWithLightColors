package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;

/**
 * The CommandQueue collects commands, so that they can be produced and processed asynchronously.
 * @author Curdin Banzer
 */
public interface ICommandQueue {

	/**
	 * Puts a command into the CommandQueue.
	 * @param command The command to add to the queue.
	 */
	void putCommand(BaseCommand command);
	
	/**
	 * Gets a command out of the CommandQueue.
	 * @return The command.
	 */
	BaseCommand getCommand();
}