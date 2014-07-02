package ch.hslu.livingWithLightColors.BusinessLogic;

import ch.hslu.livingWithLightColors.BusinessLogic.Abstraction.ILightColorsServer;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IBridge;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IBridgeListener;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandExecutor;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ICommandQueue;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ILightController;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.ILightGroupListener;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IMessageInterpreter;
import ch.hslu.livingWithLightColors.BusinessLogic.Interfaces.IRGBToPhilipsHSBConverter;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * This class binds interfaces to concrete classes, for dependency injection.
 * @author Curdin Banzer
 */
public class ServerModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(ILightColorsServer.class).to(LightColorsServer.class).in(Singleton.class);
		bind(ICommandExecutor.class).to(PhilipsCommandExecutor.class).in(Singleton.class);
		bind(IMessageInterpreter.class).to(SifteoMessageInterpreter.class);
		bind(IBridgeListener.class).to(PhilipsBridgeListener.class);
		bind(IBridge.class).to(PhilipsBridge.class).in(Singleton.class);
		bind(ILightController.class).to(PhilipsLightController.class).in(Singleton.class);
		bind(IRGBToPhilipsHSBConverter.class).to(RGBToPhilipsHSBConverter.class);
		bind(ILightGroupListener.class).to(PhilipsLightGroupListener.class).in(Singleton.class);
		bind(ICommandQueue.class).to(CommandQueue.class).in(Singleton.class);
	}
}
