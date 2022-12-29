package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemStack;
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

public class PlayerEntityDataStoreProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityDeath(LivingDeathEvent event) {
			if (event != null && event.getEntity() != null) {
				Entity entity = event.getEntity();
				Entity sourceentity = event.getSource().getTrueSource();
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
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure PlayerEntityDataStore!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency sourceentity for procedure PlayerEntityDataStore!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		File DataStorageFile = new File("");
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		String DataText = "";
		String DeathType = "";
		if (SavedEventsConfiguration.ENTITYEVENTS.get() == true) {
			DataText = ("Data Stored At Time " + Calendar.getInstance().getTime().toString()
					+ ", Type, Entity Killed. Following Data Contains Information On Subject.. " + " Entity " + entity.getDisplayName().getString()
					+ " Killed By " + sourceentity.getDisplayName().getString() + " At  X" + entity.getPosX() + ", Y" + entity.getPosY() + "Z"
					+ entity.getPosZ() + ". Killer Entity " + sourceentity.getDisplayName().getString() + " Located At  X" + sourceentity.getPosX()
					+ ", Y" + sourceentity.getPosY() + "Z" + sourceentity.getPosZ() + ", Using Item "
					+ (((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY)
							.getDisplayName().getString())
					+ ", Item Enchanted:"
					+ ((((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY))
							.isEnchanted())
					+ ", Item Durability "
					+ ((((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY))
							.getMaxDamage()
							- (((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY))
									.getDamage())
					+ "/"
					+ ((((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY))
							.getMaxDamage())
					+ ", Health Of Killer Entity " + ((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHealth() : -1) + "/"
					+ ((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getMaxHealth() : -1));
			DeathType = "Kill";
			DataStorageFile = (File) new File(
					(FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
							+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\Death"),
					File.separator + (entity.getDisplayName().getString() + "-Type-" + DeathType + Calendar.getInstance().get(Calendar.SECOND) + "-"
							+ Calendar.getInstance().get(Calendar.MINUTE) + "-" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
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
