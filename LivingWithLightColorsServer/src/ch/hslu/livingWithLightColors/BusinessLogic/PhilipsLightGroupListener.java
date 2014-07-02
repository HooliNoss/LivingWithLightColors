package ch.hslu.livingWithLightColors.BusinessLogic;

import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ILightGroupListener;
import ch.hslu.livingWithLightColors.DataAccess.Abstraction.ILoggerFactory;

import com.google.inject.Inject;
import com.philips.lighting.hue.listener.PHGroupListener;
import com.philips.lighting.model.PHHueError;

/**
 * Listens for state changes of light groups.
 * Specific implementation for Philips Hue
 * @author Stefan
 *
 */
public class PhilipsLightGroupListener extends PHGroupListener implements
		ILightGroupListener {

	private Logger logger;

	@Inject
	public PhilipsLightGroupListener(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(this.getClass());
	}

	@Override
	public void onSuccess() {
		logger.info("LISTENER: Successful.");
	}

	@Override
	public void onStateUpdate(Hashtable<String, String> successMessage,
			List<PHHueError> errorMessage) {

		if (errorMessage != null && errorMessage.size() == 0) {
			logger.info("LISTENER: Update successful.");
		} else {
			logger.warning("LISTENER: Update error.");
		}
	}

	@Override
	public void onError(int errorCode, String errorMessage) {
		logger.info("LISTENER: Error.");
	}
}
