package net.h8sh.playermod.world.biome;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;

public class ModBiomes {
    public static final ResourceKey<Biome> VOID_BIOME = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(PlayerMod.MODID, "void_biome"));

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(VOID_BIOME, voidBiome(context));
    }

    public static Biome voidBiome(BootstapContext<Biome> context) {
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        return (new Biome.BiomeBuilder()).hasPrecipitation(false).temperature(0).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(10518688).skyColor(0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomeBuilder.build()).build();
    }

}
