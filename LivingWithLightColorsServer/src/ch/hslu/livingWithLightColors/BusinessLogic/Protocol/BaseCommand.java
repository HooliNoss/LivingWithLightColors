package ch.hslu.livingWithLightColors.BusinessLogic.Protocol;

/**
 * Abstracts the base command. All commands are inheritanced by this class. 
 * @author Stefan
 *
 */
public abstract class BaseCommand {
	
	private Commands command;

	/**
	 * Gets the type of the command.
	 * @return Command type
	 */
	public Commands getCommand() {
		return command;
	}

	/**
	 * Sets the type of the command
	 * @param Command type
	 */
	public void setCommand(Commands command) {
		this.command = command;
	}

}
