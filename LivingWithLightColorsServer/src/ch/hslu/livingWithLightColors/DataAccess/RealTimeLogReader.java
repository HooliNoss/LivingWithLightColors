package ch.hslu.livingWithLightColors.DataAccess;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogListener;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILogReader;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.IPropertiesAccessor;

import com.google.inject.Inject;

/**
 * Log readers read log messages and inform their listeners.
 * Specific implementation for Sifteo Cubes
 * @author Stefan
 *
 */
public class RealTimeLogReader implements ILogReader {

	private List<ILogListener> logListeners;
	private volatile boolean running = true;
	private Process listenProcess;
	private InputStreamReader inputReader;
	private IPropertiesAccessor propertiesAccessor;
	private Logger logger;
	
	@Inject
	public RealTimeLogReader(IPropertiesAccessor accessor, ILoggerFactory loggerFactory)
	{
		this.logListeners = new ArrayList<ILogListener>();
		this.propertiesAccessor = accessor;
		this.logger = loggerFactory.getLogger(this.getClass());
	}
	
	@Override
	public void run() {

		try
		{      
			String[] envp = {"SDK_DIR="+propertiesAccessor.getSdkDir(), "set PATH="+propertiesAccessor.getPath()};
			File dir = new File(propertiesAccessor.getProjectFolder());
			listenProcess = Runtime.getRuntime().exec(propertiesAccessor.getStartCmd(),envp, dir); 

			inputReader = new InputStreamReader(listenProcess.getInputStream());

            int data;
            logger.info("RealtimeLogReader is listening now...");
            while (running)
            {

            	String message = new String();
	            while (inputReader.ready()) 
	            { 
	            	data = inputReader.read();
	            	String input = String.valueOf((char) data);
	            	
	            	if (!input.equals("!"))
	            	{
	            		message += input;
	            	}
	            	if (input.equals("\n"))
	            	{
	            		logger.info("Log message received: "+message.split("\n")[0]);
	            		break;
	            	}
	            }
	            
	            if (!message.isEmpty())
	            {
	        		for (ILogListener listener : logListeners)
	        		{
	        			listener.newMessageReceived(message);
	        		}
	            }
	            Thread.sleep(50);
            }
            
            inputReader.close();
            listenProcess.destroy();
		}
		catch(Exception e)
		{
			logger.warning(e.getMessage());
		}
			running = false;
	}

	@Override
	public void stop() {
		running = false;
		try {
			inputReader.close();
			listenProcess.destroy();
			
			logger.info("listenProcess killed");
			
			Runtime.getRuntime().exec(propertiesAccessor.getKillCmd());
			
			logger.info("swiss should be killed");
					
			} catch (IOException e) {
				logger.warning(e.getMessage());
			}
	}

	@Override
	public void addLogListener(ILogListener listener) {
		logListeners.add(listener);
	}

	@Override
	public void removeLogListener(ILogListener listener) {
		logListeners.remove(listener);		
	}
}
