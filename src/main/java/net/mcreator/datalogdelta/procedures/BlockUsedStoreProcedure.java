package net.mcreator.datalogdelta.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

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

public class BlockUsedStoreProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
			PlayerEntity entity = event.getPlayer();
			if (event.getHand() != entity.getActiveHand()) {
				return;
			}
			double i = event.getPos().getX();
			double j = event.getPos().getY();
			double k = event.getPos().getZ();
			IWorld world = event.getWorld();
			BlockState state = world.getBlockState(event.getPos());
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("direction", event.getFace());
			dependencies.put("blockstate", state);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency world for procedure BlockUsedStore!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency x for procedure BlockUsedStore!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency y for procedure BlockUsedStore!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency z for procedure BlockUsedStore!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DatalogdeltaMod.LOGGER.warn("Failed to load dependency entity for procedure BlockUsedStore!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		File DataStorageFile = new File("");
		String DataType = "";
		String DataText = "";
		com.google.gson.JsonObject JSOBJCT = new com.google.gson.JsonObject();
		if (SavedEventsConfiguration.BLOCKEVENTS.get() == true) {
			if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.BEACON
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.REPEATER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.REPEATER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.COMPARATOR
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.COMPARATOR
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.BREWING_STAND
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CAULDRON
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.BELL
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRINDSTONE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.STONECUTTER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.BARREL
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.RESPAWN_ANCHOR
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.LODESTONE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CHEST
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.ENDER_CHEST
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FURNACE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.BLAST_FURNACE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CAMPFIRE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.SOUL_CAMPFIRE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.ENCHANTING_TABLE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CAKE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.LEVER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.DISPENSER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.DROPPER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.OBSERVER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.HOPPER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.LECTERN
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.NOTE_BLOCK
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.TRAPPED_CHEST
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.SMOKER
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.JUKEBOX
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CRAFTING_TABLE
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.ANVIL
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CHIPPED_ANVIL
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CHIPPED_ANVIL
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.COMMAND_BLOCK
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.REPEATING_COMMAND_BLOCK
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CHAIN_COMMAND_BLOCK) {
				DataText = ("Player " + entity.getDisplayName().getString() + " Used Block "
						+ (new ItemStack((world.getBlockState(new BlockPos(x, y, z))).getBlock())).getDisplayName().getString() + " At X " + x
						+ ", Y " + y + ", Z " + z);
				DataType = ("RC" + new ItemStack((world.getBlockState(new BlockPos(x, y, z))).getBlock()));
				DataStorageFile = (File) new File(
						(FMLPaths.GAMEDIR.get().toString() + "\\DataLogs\\"
								+ new java.text.SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\\BlockUsage"),
						File.separator + (entity.getDisplayName().getString() + "-Type-" + DataType + Calendar.getInstance().get(Calendar.SECOND)
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
			}
		}
	}
}
