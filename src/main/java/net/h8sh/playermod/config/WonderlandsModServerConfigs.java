package net.h8sh.playermod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WonderlandsModServerConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.IntValue CHUNK_MIN_MANA;
    public static ForgeConfigSpec.IntValue CHUNK_MAX_MANA;


    static {
        BUILDER.push("Configs for Wonderlands Mod");

        CHUNK_MIN_MANA = BUILDER
                .comment("Minimum amount of mana in a chunk")
                .defineInRange("minMana", 10, 0, Integer.MAX_VALUE);
        CHUNK_MAX_MANA = BUILDER
                .comment("Maximum amount of mana in a chunk")
                .defineInRange("maxMana", 100, 1, Integer.MAX_VALUE);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}