package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.datalogdelta.DatalogdeltaModVariables;
import net.mcreator.datalogdelta.DatalogdeltaMod;

import java.util.Random;
import java.util.Map;
import java.util.Calendar;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class StoreLocationCommandProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure StoreLocationCommand!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		File DataStorageFile = new File("");
		String DataText = "";
		DataText = ("Player " + entity.getDisplayName().getString() + " Manually Saved Their Coords At X " + Math.round(entity.getPosX()) + ", Y "
				+ Math.round(entity.getPosY()) + ", Z " + Math.round(entity.getPosZ()));
		DataStorageFile = (File) new File(
				(FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
						+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\ManualXYZNote"),
				File.separator + (entity.getDisplayName().getString() + "-type-" + "StoredLocation" + Calendar.getInstance().get(Calendar.SECOND)
						+ "-" + Calendar.getInstance().get(Calendar.MINUTE) + "-" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "-"
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
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(
					new StringTextComponent(("Message \"" + "" + DataText + "\" Saved To "
							+ (FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
									+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\ManualXYZNote"))),
					(false));
		}
		DatalogdeltaModVariables.LastRecEventMessage = DataText;
	}
}
