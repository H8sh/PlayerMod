package net.h8sh.playermod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WonderlandsModClientConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.IntValue MANA_HUD_X;
    public static ForgeConfigSpec.IntValue MANA_HUD_Y;
    public static ForgeConfigSpec.IntValue MANA_HUD_COLOR;
    public static ForgeConfigSpec.IntValue CRYSTAL_HUD_X;
    public static ForgeConfigSpec.IntValue CRYSTAL_HUD_Y;
    public static ForgeConfigSpec.IntValue CRYSTAL_HUD_COLOR;

    static {
        BUILDER.push("Configs for Wonderlands Mod");

        MANA_HUD_X = BUILDER
                .comment("x location of the mana hud")
                .defineInRange("manaHUDx", 10, -1, Integer.MAX_VALUE);
        MANA_HUD_Y = BUILDER
                .comment("y location of the mana hud")
                .defineInRange("manaHUDy", 10, -1, Integer.MAX_VALUE);
        MANA_HUD_COLOR = BUILDER
                .comment("Color of the mana hud")
                .defineInRange("manaHUDColor", 0xffffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);
        CRYSTAL_HUD_X = BUILDER
                .comment("x location of the crystal hud")
                .defineInRange("crystalHUDx", 10, -1, Integer.MAX_VALUE);
        CRYSTAL_HUD_Y = BUILDER
                .comment("y location of the crystal hud")
                .defineInRange("crystalHUDy", 10, -1, Integer.MAX_VALUE);
        CRYSTAL_HUD_COLOR = BUILDER
                .comment("Color of the crystal hud")
                .defineInRange("crystalHUDColor", 0xff55FF55, Integer.MIN_VALUE, Integer.MAX_VALUE);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}