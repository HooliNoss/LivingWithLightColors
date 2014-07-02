package ch.hslu.livingWithLightColors.DataAccess.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogListener;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogReader;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Inject;

/**
 * Mocks the RealTimeLogReader for testing reasons. Generates commands with the frequency, specified in the constant "COMMAND_FREQUENCY".
 * @author Curdin Banzer
 *
 */
public class RealTimeLogReaderMock implements ILogReader {

	private static final int COMMAND_FREQUENCY = 500;
	private List<ILogListener> logListeners;
	private boolean running = true;
	private Random random = new Random();
	private boolean on = false;
	private Logger logger;

	@Inject
	public RealTimeLogReaderMock(ILoggerFactory loggerFactory) {
		logListeners = new ArrayList<ILogListener>();
		this.logger = loggerFactory.getLogger(this.getClass());
	}

	@Override
	public void run() {
		try {
			while (running) {
				
				String message = generateMessage();
				//String message = generateMaxValueMessage();
				
				for (ILogListener listener : logListeners) {
					listener.newMessageReceived(message);
					logger.fine("Message sent");
				}
				Thread.sleep(COMMAND_FREQUENCY);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public void addLogListener(ILogListener listener) {
		logListeners.add(listener);
	}

	@Override
	public void removeLogListener(ILogListener listener) {
		logListeners.remove(listener);
	}

	/**
	 * Generates random messages from (simulated) sifteo cubes.
	 * 
	 * @return message that contains a command
	 */
	private String generateMessage() {
		if (!on) {
			on = true;
			return "#COMMAND:LightBulbOn:\n";

		}
		String allCommands[] = { "#COMMAND:LightBulbOff:\n",
				"#COMMAND:LightBulbNewColor:", "#COMMAND:LightBulbNewBrightness:"};
		int commandIndex = random.nextInt(allCommands.length);
		String selectedCommand = allCommands[commandIndex];
		if (selectedCommand.equals("#COMMAND:LightBulbNewColor:")) {
			selectedCommand += String.format("%d:%d:%d:\n",
					random.nextInt(256), random.nextInt(256),
					random.nextInt(256));
		}
		if(selectedCommand.equals("#COMMAND:LightBulbNewBrightness:")){
			selectedCommand += String.format("%d:\n", random.nextInt(256));
		}
		if(selectedCommand.equals("#COMMAND:LightBulbOff:\n")){
			on = false;
		}
		return selectedCommand;
	}
	
	/**
	 * Generates random messages from (simulated) sifteo cubes.
	 * 
	 * @return message that contains a command
	 */
	private String generateMaxValueMessage() {
		if (!on) {
			on = true;
			return "#COMMAND:LightBulbOn:\n";

		}
		String allCommands[] = { "#COMMAND:LightBulbOff:\n",
				"#COMMAND:LightBulbNewColor:", "#COMMAND:LightBulbNewBrightness:"};
		int commandIndex = random.nextInt(allCommands.length);
		String selectedCommand = allCommands[commandIndex];
		if (selectedCommand.equals("#COMMAND:LightBulbNewColor:")) {
			selectedCommand += String.format("%d:%d:%d:\n",
					255, 255,
					255);
		}
		if(selectedCommand.equals("#COMMAND:LightBulbNewBrightness:")){
			selectedCommand += String.format("%d:\n", 255);
		}
		if(selectedCommand.equals("#COMMAND:LightBulbOff:\n")){
			on = false;
		}
		return selectedCommand;
	}
}
