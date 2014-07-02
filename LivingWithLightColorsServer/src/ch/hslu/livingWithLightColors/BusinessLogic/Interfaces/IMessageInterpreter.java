package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;

/**
 * Interprets messages, received from the remote control.
 * @author Curdin Banzer
 *
 */
public interface IMessageInterpreter {
	
	/**
	 * Converts a message to an BaseCommand. BaseCommands can then be executed, to change the light state.
	 * @param message	Message to convert into an BaseCommand.
	 * @return			BaseCommand, generated out of the message.
	 */
	BaseCommand interpreteMessage(String message);

}
