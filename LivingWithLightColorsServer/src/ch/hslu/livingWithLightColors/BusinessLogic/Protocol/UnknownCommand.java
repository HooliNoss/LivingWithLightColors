package ch.hslu.livingWithLightColors.BusinessLogic.Protocol;

/**
 * Unknown command. If the MessageInterpreter could not resolve the string into a command. This command will 
 * be generated. 
 * @author Stefan
 *
 */
public class UnknownCommand extends BaseCommand {
	
	private String message;
	
	public UnknownCommand()
	{
		setCommand(Commands.Unknown);
		setMessage("Unknown command");
	}

	/**
	 * Gets the message string of the unknown command
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message string of the unknown command
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
