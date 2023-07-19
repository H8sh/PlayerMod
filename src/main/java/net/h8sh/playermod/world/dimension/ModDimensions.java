package net.h8sh.playermod.world.dimension;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {

    public static final ResourceKey<Level> WONDERLANDS_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "wonderlands"));
    public static final ResourceKey<DimensionType> WONDERLANDS_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, WONDERLANDS_KEY.location());

    public static void register() {
        System.out.println("Registering ModDimensions for " + PlayerMod.MODID);
    }

}
