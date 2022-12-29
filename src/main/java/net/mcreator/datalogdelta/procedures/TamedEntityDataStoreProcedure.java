package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.AnimalTameEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.LivingEntity;
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

public class TamedEntityDataStoreProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityTamed(AnimalTameEvent event) {
			Entity entity = event.getAnimal();
			Entity sourceentity = event.getTamer();
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			World world = entity.world;
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("sourceentity", sourceentity);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure TamedEntityDataStore!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency sourceentity for procedure TamedEntityDataStore!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		File DataStorageFile = new File("");
		String DataType = "";
		String DataText = "";
		if (SavedEventsConfiguration.ENTITYEVENTS.get() == true) {
			DataText = ("Entity " + entity.getDisplayName().getString() + " Tamed At X " + entity.getPosX() + ", Y " + entity.getPosY() + ", Z "
					+ entity.getPosZ() + " By Entity " + sourceentity.getDisplayName().getString() + " At X " + sourceentity.getPosX() + ", Y "
					+ sourceentity.getPosY() + ", Z " + sourceentity.getPosZ() + ". Is Tamed Entity Child:"
					+ ((entity instanceof LivingEntity) ? ((LivingEntity) entity).isChild() : false) + ". Tamed Entity HP "
					+ ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) + "/"
					+ ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMaxHealth() : -1) + ", Tamer Entity Hp "
					+ ((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHealth() : -1) + "/"
					+ ((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getMaxHealth() : -1));
			DataType = "Tamed";
			DataStorageFile = (File) new File(
					(FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
							+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\EntityTamed"),
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
