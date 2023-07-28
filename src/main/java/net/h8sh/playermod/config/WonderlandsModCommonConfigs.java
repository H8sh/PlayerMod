package net.h8sh.playermod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WonderlandsModCommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Configs for Wonderlands Mod");

        //TODO:configs

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}