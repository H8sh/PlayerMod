package net.h8sh.playermod.world.noise;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;

import java.util.List;

public class ModNoiseSettings {

    public static final ResourceKey<NoiseGeneratorSettings> VOID_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(PlayerMod.MODID, "void"));
    protected static final NoiseSettings VOID_NOISE_SETTINGS = NoiseSettings.create(0, 256, 1, 2);

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> pContext) {
        pContext.register(VOID_NOISE, voidd());
    }

    public static NoiseGeneratorSettings voidd() {
        return new NoiseGeneratorSettings(VOID_NOISE_SETTINGS, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), none(), SurfaceRuleData.air(), List.of(), 63, false, false, false, false);
    }

    protected static NoiseRouter none() {
        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
    }

}
