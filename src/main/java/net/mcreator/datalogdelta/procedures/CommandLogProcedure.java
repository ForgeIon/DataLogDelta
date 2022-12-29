package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.CommandEvent;

import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.command.CommandSource;

import net.mcreator.datalogdelta.DatalogdeltaMod;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.mojang.brigadier.context.CommandContext;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class CommandLogProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onCommand(CommandEvent event) {
			Entity entity = event.getParseResults().getContext().getSource().getEntity();
			if (entity != null) {
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				CommandContext<CommandSource> ctx = event.getParseResults().getContext().build(event.getParseResults().getReader().getString());
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", entity.world);
				dependencies.put("entity", entity);
				dependencies.put("command", event.getParseResults().getReader().getString());
				dependencies.put("arguments", ctx);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure CommandLog!");
			return;
		}
		if (dependencies.get("command") == null) {
			if (!dependencies.containsKey("command"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency command for procedure CommandLog!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		String command = (String) dependencies.get("command");
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		File DataStorageFile = new File("");
		String DataType = "";
		String DataText = "";
		DataText = ("Entity " + entity.getDisplayName().getString() + " Executed Command " + command);
		DataType = "Command";
		DataStorageFile = (File) new File(
				(FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
						+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\Chat"),
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
