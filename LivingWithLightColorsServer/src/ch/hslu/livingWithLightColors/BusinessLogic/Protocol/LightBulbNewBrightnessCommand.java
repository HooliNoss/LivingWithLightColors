package ch.hslu.livingWithLightColors.BusinessLogic.Protocol;

/**
 * Command for new brightness. 
 * @author Stefan
 *
 */
public class LightBulbNewBrightnessCommand extends BaseCommand{
	
	private int brightness;
	

	public LightBulbNewBrightnessCommand()
	{
		setCommand(Commands.LightBulbNewBrightness);
	}	
	
	/**
	 * Gets the brightness of the command
	 * @return brightness as integer between 0 and 254
	 */
	public int getBrightness() {
		return brightness;
	}
	
	/**
	 * Sets the brightness of the command
	 * @param brightness as integer between 0 and 254
	 */
	public void setBrightness(int brightness){
		this.brightness = brightness;
	}
}
