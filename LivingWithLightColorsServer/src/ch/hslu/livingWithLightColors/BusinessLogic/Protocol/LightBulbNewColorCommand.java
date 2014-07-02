package ch.hslu.livingWithLightColors.BusinessLogic.Protocol;

/**
 * Command for a new color.
 * @author Stefan
 *
 */
public class LightBulbNewColorCommand extends BaseCommand{
	
	private int rgbRed;
	private int rgbGreen;
	private int rgbBlue;
	
	public LightBulbNewColorCommand()
	{
		setCommand(Commands.LightBulbNewColor);
	}
	
	/**
	 * Gets the blue value of the RGB color space
	 * @return blue value as integer between 0 and 254
	 */
	public int getRgbBlue() {
		return rgbBlue;
	}
	/**
	 * Sets the blue value of the RGB color space
	 * @param blue value as integer between 0 and 254
	 */
	public void setRgbBlue(int rgbBlue) {
		this.rgbBlue = rgbBlue;
	}
	/**
	 * Gets the green value of the RGB color space
	 * @return green value as integer between 0 and 254
	 */
	public int getRgbGreen() {
		return rgbGreen;
	}
	/**
	 * Sets the green value of the RGB color space
	 * @param green value as integer between 0 and 254
	 */
	public void setRgbGreen(int rgbGreen) {
		this.rgbGreen = rgbGreen;
	}
	/**
	 * Gets the red value of the RGB color space
	 * @return red value as integer between 0 and 254
	 */
	public int getRgbRed() {
		return rgbRed;
	}
	/**
	 * Sets the red value of the RGB color space
	 * @param red value as integer between 0 and 254
	 */
	public void setRgbRed(int rgbRed) {
		this.rgbRed = rgbRed;
	}

}
