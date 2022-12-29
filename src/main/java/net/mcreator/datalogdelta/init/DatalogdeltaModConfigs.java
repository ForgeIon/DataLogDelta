package net.mcreator.datalogdelta.init;

import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.mcreator.datalogdelta.configuration.SavedEventsConfiguration;

@Mod.EventBusSubscriber(modid = "datalogdelta", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DatalogdeltaModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SavedEventsConfiguration.SPEC, "StEvents.toml");
		});
	}
}
