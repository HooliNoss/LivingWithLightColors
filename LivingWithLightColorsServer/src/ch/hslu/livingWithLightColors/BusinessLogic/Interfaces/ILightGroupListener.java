package ch.hslu.livingWithLightColors.BusinessLogic.Interfaces;

import java.util.Hashtable;
import java.util.List;

import com.philips.lighting.model.PHHueError;

/**
 * Listens for state changes of light groups.
 * @author Curdin Banzer
 *
 */
public interface ILightGroupListener {

	/**
	 * Is called, when a command was executed successfully.
	 */
	public abstract void onSuccess();

	/**
	 * Is called, when a state changed happened.
	 * @param successMessage	Message, if the change was successful.
	 * @param errorMessage		Message, if the change failed.
	 */
	public abstract void onStateUpdate(Hashtable<String, String> successMessage,
			List<PHHueError> errorMessage);

	/**
	 * Is called, when the execution of a command has failed.
	 * @param errorCode			The error code (see philips hue sdk).
	 * @param errorMessage		The error message.
	 */
	public abstract void onError(int errorCode, String errorMessage);

}