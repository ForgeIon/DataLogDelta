package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;

import net.mcreator.datalogdelta.configuration.SavedEventsConfiguration;
import net.mcreator.datalogdelta.DatalogdeltaMod;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class PlayerJoinsProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			Entity entity = event.getPlayer();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", entity.getPosX());
			dependencies.put("y", entity.getPosY());
			dependencies.put("z", entity.getPosZ());
			dependencies.put("world", entity.world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency x for procedure PlayerJoins!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency y for procedure PlayerJoins!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency z for procedure PlayerJoins!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure PlayerJoins!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		File DataStorageFile = new File("");
		String DataType = "";
		String DataText = "";
		if (SavedEventsConfiguration.ENTITYEVENTS.get() == true) {
			DataText = ("Player " + entity.getDisplayName().getString() + "Logged Off at X " + x + ", Y " + y + ", Z " + z);
			DataType = "Join";
			DataStorageFile = (File) new File(
					(FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
							+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\PlayerJoinLeave"),
					File.separator + (entity.getDisplayName().getString() + "-Type-" + DataType + Calendar.getInstance().get(Calendar.SECOND) + "-"
							+ Calendar.getInstance().get(Calendar.MINUTE) + "-" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "-"
							+ MathHelper.nextDouble(new Random(), 1, 25) + ".txt"));
			if (DataStorageFile.exists()) {
				JSOBJCT.addProperty("DataLog", DataText);
				{
					Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileWriter fileWriter = new FileWriter(DataStorageFile);
						fileWriter.write(mainGSONBuilderVariable.toJson(JSOBJCT));
						fileWriter.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			} else {
				try {
					DataStorageFile.getParentFile().mkdirs();
					DataStorageFile.createNewFile();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				JSOBJCT.addProperty("DataLog", DataText);
				{
					Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileWriter fileWriter = new FileWriter(DataStorageFile);
						fileWriter.write(mainGSONBuilderVariable.toJson(JSOBJCT));
						fileWriter.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}
}
