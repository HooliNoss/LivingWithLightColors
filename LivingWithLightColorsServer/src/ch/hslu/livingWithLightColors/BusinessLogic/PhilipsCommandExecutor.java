package ch.hslu.livingWithLightColors.BusinessLogic;

import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandExecutor;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandQueue;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ILightController;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IRGBToPhilipsHSBConverter;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewBrightnessCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.LightBulbNewColorCommand;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.PhilipsHSBLightSetting;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Inject;

/**
 * Executes commands for controlling the light.
 * Specific implementation for Philips Hue
 * @author Stefan
 *
 */
public class PhilipsCommandExecutor implements ICommandExecutor {

	private ILightController lightController;
	private IRGBToPhilipsHSBConverter hsbConverter;
	private volatile boolean running = true;
	private ICommandQueue commandQueue;
	private Logger logger;
	private static final int COMMAND_FREQUENCY = 400;

	@Inject
	public PhilipsCommandExecutor(ILightController lightController,
			IRGBToPhilipsHSBConverter converter, ICommandQueue commandQueue,
			ILoggerFactory loggerFactory) {
		this.lightController = lightController;
		this.hsbConverter = converter;
		this.commandQueue = commandQueue;
		this.logger = loggerFactory.getLogger(this.getClass());
	}

	private void executeCommand(BaseCommand command) {

		switch (command.getCommand()) {
		case LightBulbNewColor:

			LightBulbNewColorCommand newColorCommand = (LightBulbNewColorCommand) command;
			PhilipsHSBLightSetting lightSetting = hsbConverter.convert(
					newColorCommand.getRgbRed(), newColorCommand.getRgbGreen(),
					newColorCommand.getRgbBlue());
			lightController.setColor(lightSetting);
			break;

		case LightBulbOff:
			lightController.turnOff();
			break;

		case LightBulbOn:
			lightController.turnOn();
			break;
			
		case LightBulbNewBrightness:
			LightBulbNewBrightnessCommand newBrightnessCommand = (LightBulbNewBrightnessCommand) command;
			lightController.setBrightness(newBrightnessCommand.getBrightness());
			break;

		case Unknown:
			break;

		default:
			break;
		}
	}

	@Override
	public void run() {
		logger.info("CommandExecutor running");
		while (running) {
			try {
				BaseCommand nextCommand = commandQueue.getCommand();
				if (nextCommand != null && lightController.isBridgeConnected()) {
						logger.fine("Execute Command");
						executeCommand(nextCommand);
						Thread.sleep(COMMAND_FREQUENCY);
				}
				else{
					logger.fine("No commands waiting");
				}
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}
		}
	}

	@Override
	public void stop() {
		running = false;
	}

}
