package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

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

public class ItemPickupDataStoreProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPickup(EntityItemPickupEvent event) {
			PlayerEntity entity = event.getPlayer();
			ItemStack itemstack = event.getItem().getItem();
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
			dependencies.put("itemstack", itemstack);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency x for procedure ItemPickupDataStore!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency y for procedure ItemPickupDataStore!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency z for procedure ItemPickupDataStore!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure ItemPickupDataStore!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency itemstack for procedure ItemPickupDataStore!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		File DataStorageFile = new File("");
		ItemStack ItemStackExtra = ItemStack.EMPTY;
		String DataType = "";
		String DataText = "";
		DataText = ("Entity " + entity.getDisplayName().getString() + " Picked Up Item At X " + x + ", Y " + y + ", Z " + z + ", Item Type "
				+ itemstack.getDisplayName().getString() + ", Item Count " + (itemstack).getCount() + ", Item Durability "
				+ ((itemstack).getMaxDamage() - (itemstack).getDamage()) + "/" + (itemstack).getMaxDamage() + ", Is Item Enchanted:"
				+ (itemstack).isEnchanted() + ", Is Item Is Food:" + itemstack.getItem().isFood() + ", If Food, Item Food Value "
				+ (itemstack.getItem().isFood() ? itemstack.getItem().getFood().getHealing() : 0) + ", If Food, Item Sat Value "
				+ (itemstack.getItem().isFood() ? itemstack.getItem().getFood().getSaturation() : 0));
		DataType = "ItemPickup";
		DataStorageFile = (File) new File(
				(FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
						+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\Item"),
				File.separator + (entity.getDisplayName().getString() + "-Item-" + itemstack.getDisplayName().getString()
						+ Calendar.getInstance().get(Calendar.SECOND) + "-" + Calendar.getInstance().get(Calendar.MINUTE) + "-"
						+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "-" + MathHelper.nextDouble(new Random(), 1, 25) + ".txt"));
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
