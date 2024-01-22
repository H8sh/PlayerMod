package net.h8sh.playermod.datagen;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.world.biome.ModBiomes;
import net.h8sh.playermod.world.dimension.ModDimensions;
import net.h8sh.playermod.world.noise.ModNoiseSettings;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem)
            .add(Registries.BIOME, ModBiomes::boostrap)
            .add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(PlayerMod.MODID));
    }
}
