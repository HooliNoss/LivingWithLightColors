package ch.hslu.livingWithLightColors.BusinessLogic.Protocol;

/**
 * Command to turn the light off
 * @author Stefan
 *
 */
public class LightBulbOffCommand extends BaseCommand{
	
	public LightBulbOffCommand()
	{
		setCommand(Commands.LightBulbOff);
	}

}
