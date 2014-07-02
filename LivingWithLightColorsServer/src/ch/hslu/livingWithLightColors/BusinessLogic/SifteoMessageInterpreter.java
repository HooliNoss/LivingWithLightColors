package ch.hslu.livingWithLightColors.BusinessLogic;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IMessageInterpreter;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.Commands;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewBrightnessCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewColorCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbOffCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbOnCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.UnknownCommand;

/**
 * Interprets messages, received from the remote control.
 * Specific implementation for Sifteo Cubes
 * @author Stefan
 *
 */
public class SifteoMessageInterpreter implements IMessageInterpreter{

	private static final String PROTOCOL_INDICATOR = "#COMMAND";
	private static final String PROTOCOL_SEPERATOR = ":";
	
	public SifteoMessageInterpreter()
	{
	}
	
	@Override
	public BaseCommand interpreteMessage(String message) {
		
		BaseCommand command = new UnknownCommand();
		String[] splitted = message.split(PROTOCOL_SEPERATOR);
		
		if (splitted != null)
		{
			String prefix = splitted[0];
			
			if (prefix.equals(PROTOCOL_INDICATOR))
			{
				command = interpreteProtocolMessage(splitted);
			}
			else
			{
				((UnknownCommand) command).setMessage(message);	
			}
		}
		
		return command;
	}
	
	private BaseCommand interpreteProtocolMessage(String[] commandStr)
	{
		BaseCommand command = new UnknownCommand();
		
		try
		{
			if (commandStr[1].equals(Commands.LightBulbOn.toString()))
			{
				command = new LightBulbOnCommand();
			}
			else if (commandStr[1].equals(Commands.LightBulbOff.toString()))
			{
				command = new LightBulbOffCommand();
			}
			else if (commandStr[1].equals(Commands.LightBulbNewColor.toString()))
			{
				command = new LightBulbNewColorCommand();
				
				int red = Integer.parseInt(commandStr[2]);
				int green = Integer.parseInt(commandStr[3]);
				int blue = Integer.parseInt(commandStr[4]);
				
				
				((LightBulbNewColorCommand)command).setRgbRed(red);
				((LightBulbNewColorCommand)command).setRgbGreen(green);
				((LightBulbNewColorCommand)command).setRgbBlue(blue);
				
			}
			else if(commandStr[1].equals(Commands.LightBulbNewBrightness.toString())){
				
				command = new LightBulbNewBrightnessCommand();
				
				int brightness = Integer.parseInt(commandStr[2]);
				
				((LightBulbNewBrightnessCommand)command).setBrightness(brightness);
			}
			else
			{
			}
			
			return command;
		}
		catch (Exception ex)
		{
			command = new UnknownCommand();
			String msg = "Unknown command: ";
			
			for (String s : commandStr)
			{
				msg = msg + ";" + s;
			}
			
			((UnknownCommand) command).setMessage(msg);
			
			return command;
		}
	}
}
