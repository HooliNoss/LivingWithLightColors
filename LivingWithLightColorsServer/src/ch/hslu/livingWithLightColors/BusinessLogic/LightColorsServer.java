package ch.hslu.livingWithLightColors.BusinessLogic;

import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Abstraction.ILightColorsServer;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandExecutor;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandQueue;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ILightController;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IMessageInterpreter;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogListener;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogReader;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Inject;

/**
 * The LightColorsServer is the central steering part of the application. It
 * controls the LogReader for command listening and the CommandExecutor for
 * executing them. Beside that, it opens and closes the connection to the
 * bridge.
 * @author Stefan
 *
 */
public class LightColorsServer implements ILightColorsServer, ILogListener {

	private ILogReader logReader;
	private IMessageInterpreter messageInterpreter;

	private Logger logger;
	private ILightController lightController;
	private ICommandQueue commandQueue;
	private ICommandExecutor commandExecutor;

	@Inject
	public LightColorsServer(ILogReader logReader,
			IMessageInterpreter messageInterpreter, ILoggerFactory loggerFactory,
			ILightController lightController, ICommandQueue commandQueue, ICommandExecutor commandExecutor) {
		this.logReader = logReader;
		this.messageInterpreter = messageInterpreter;
		this.logger = loggerFactory.getLogger(this.getClass());
		this.lightController = lightController;
		this.commandQueue = commandQueue;
		this.commandExecutor = commandExecutor;
	}

	@Override
	public void run() {
		lightController.open();
		logReader.addLogListener(this);
		Thread logReaderThread = new Thread(logReader, "LogReader");
		Thread commandExecutorThread =	new Thread(commandExecutor, "CommandExecutor");
		
		// Starts the logReaderThread
		logReaderThread.start();
		
		// Starts the commandExecutorThread
		commandExecutorThread.start();
				
		// Joins the threads
		try {
			logReaderThread.join();
			commandExecutorThread.join();
		} catch (InterruptedException e) {
			logger.warning(e.getMessage());
		}	
	}

	@Override
	public void stop() {				
		lightController.close();
		logReader.removeLogListener(this);
		logReader.stop();
		commandExecutor.stop();
	}

	@Override
	public void newMessageReceived(String message) {
		BaseCommand command = messageInterpreter.interpreteMessage(message);
		commandQueue.putCommand(command);
	}
}
