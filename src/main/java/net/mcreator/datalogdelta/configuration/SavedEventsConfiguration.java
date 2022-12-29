package net.mcreator.datalogdelta.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class SavedEventsConfiguration {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.ConfigValue<Boolean> BLOCKEVENTS;
	public static final ForgeConfigSpec.ConfigValue<Boolean> ENTITYEVENTS;
	public static final ForgeConfigSpec.ConfigValue<Boolean> CHATEVENTS;
	static {
		BUILDER.push("Events");
		BLOCKEVENTS = BUILDER.comment("Whether to capture block type events").define("Block Events", true);
		ENTITYEVENTS = BUILDER.comment("Whether to capture entity type events").define("Entity Events", true);
		CHATEVENTS = BUILDER.comment("Logs chat type events").define("Chat Events", true);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
