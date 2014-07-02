package ch.hslu.livingWithLightColors.BusinessLogic.Test;

import ch.hslu.livingWithLightColors.BusinessLogic.CommandQueue;
import ch.hslu.livingWithLightColors.BusinessLogic.LightColorsServer;
import ch.hslu.livingWithLightColors.BusinessLogic.PhilipsBridge;
import ch.hslu.livingWithLightColors.BusinessLogic.PhilipsBridgeListener;
import ch.hslu.livingWithLightColors.BusinessLogic.PhilipsCommandExecutor;
import ch.hslu.livingWithLightColors.BusinessLogic.PhilipsLightController;
import ch.hslu.livingWithLightColors.BusinessLogic.PhilipsLightGroupListener;
import ch.hslu.livingWithLightColors.BusinessLogic.RGBToPhilipsHSBConverter;
import ch.hslu.livingWithLightColors.BusinessLogic.SifteoMessageInterpreter;
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

public class ServerModuleMock extends AbstractModule {

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
