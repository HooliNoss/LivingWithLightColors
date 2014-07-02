package ch.hslu.livingWithLightColors.BusinessLogic;

import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandQueue;
import ch.hslu.livingWithLightColors.BusinessLogic.Protocol.BaseCommand;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Inject;

/**
 * The CommandQueue collects and stores commands, so that they can be produced and processed asynchronously.
 * @author Stefan
 *
 */
public class CommandQueue implements ICommandQueue {
	private BaseCommand[] commandQueue;
	private Logger logger;
	private static final int QUEUE_SIZE = 3;
	private static int head = -1;
	private static int tail = -1;
	private static int elements = 0;
	
	@Inject
	public CommandQueue(ILoggerFactory loggerFactory){
		this.commandQueue = new BaseCommand[QUEUE_SIZE];
		this.logger = loggerFactory.getLogger(this.getClass());
	}
	
	@Override
	public synchronized void putCommand(BaseCommand command){
		head = (head + 1) % QUEUE_SIZE;
		if(commandQueue[head] != null){
			tail = (tail + 1) % QUEUE_SIZE;
		}
		commandQueue[head] = command;
		elements = Math.min(elements+1, QUEUE_SIZE);
		logger.fine("Added command to queue. New size: "+elements);
	}
	
	@Override
	public synchronized BaseCommand getCommand(){
		if(elements > 0 && elements <= QUEUE_SIZE){
			tail = (tail + 1) % QUEUE_SIZE;
			BaseCommand command = commandQueue[tail];
			commandQueue[tail] = null;
			elements --;
			logger.fine("Removed command from queue. New size: "+elements);
			return command;		
		}
		else if(elements == 0){
			logger.fine("Command queue empty.");
			return null;
		}
		else{
			logger.warning(String.format("Queue out of bound. Head: %d | Tail %d | elements: %d.", head, tail, elements));
			return null;
		}
	}
}
