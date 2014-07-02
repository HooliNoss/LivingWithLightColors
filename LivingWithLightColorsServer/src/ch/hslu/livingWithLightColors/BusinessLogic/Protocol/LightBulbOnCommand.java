package ch.hslu.livingWithLightColors.BusinessLogic.Protocol;

/**
 * Command to turn the light on
 * @author Stefan
 *
 */
public class LightBulbOnCommand extends BaseCommand{
	
	public LightBulbOnCommand()
	{
		setCommand(Commands.LightBulbOn);
	}

}
