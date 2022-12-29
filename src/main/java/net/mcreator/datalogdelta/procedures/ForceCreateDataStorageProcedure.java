package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.command.CommandSource;

import net.mcreator.datalogdelta.DatalogdeltaMod;

import java.util.Map;
import java.util.Calendar;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.BoolArgumentType;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class ForceCreateDataStorageProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("arguments") == null) {
			if (!dependencies.containsKey("arguments"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency arguments for procedure ForceCreateDataStorage!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure ForceCreateDataStorage!");
			return;
		}
		CommandContext<CommandSource> arguments = (CommandContext<CommandSource>) dependencies.get("arguments");
		Entity entity = (Entity) dependencies.get("entity");
		String DataText = "";
		File DataStorageFile = new File("");
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		if (BoolArgumentType.getBool(arguments, "OverrideDataStorageName") == false) {
			DataText = ("DataLog" + new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + ".txt");
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("Generated Text File, File Name: " + DataText)), (true));
			}
		} else {
			DataText = ((new Object() {
				public String getMessage() {
					try {
						return MessageArgument.getMessage(arguments, "StorageNameOverride").getString();
					} catch (CommandSyntaxException ignored) {
						return "";
					}
				}
			}).getMessage() + ".txt");
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Notice, This is for Debug purposes, This will not be used!"),
						(true));
			}
		}
		DataStorageFile = (File) new File((FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
				+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())), File.separator + DataText);
		if (!DataStorageFile.exists()) {
			try {
				DataStorageFile.getParentFile().mkdirs();
				DataStorageFile.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			JSOBJCT.addProperty("DataLogGenTime", ("Datalog Generated At Time " + Calendar.getInstance().getTime().toString()));
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
			JSOBJCT.addProperty("DataLogGenTime", ("Datalog Generated At Time " + Calendar.getInstance().getTime().toString()));
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
