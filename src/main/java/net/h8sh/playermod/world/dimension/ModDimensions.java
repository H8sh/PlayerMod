package net.h8sh.playermod.world.dimension;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.world.biome.ModBiomes;
import net.h8sh.playermod.world.noise.ModNoiseSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ModDimensions {


    public static final ResourceKey<LevelStem> WONDERLANDS_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PlayerMod.MODID, "wonderlands"));
    public static final ResourceKey<Level> WONDERLANDS_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "wonderlands"));
    public static final ResourceKey<DimensionType> WONDERLANDS_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PlayerMod.MODID, "wonderlands_type"));

    public static final ResourceKey<LevelStem> HUNTED_MANSION_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PlayerMod.MODID, "hunted_mansion"));
    public static final ResourceKey<Level> HUNTED_MANSION_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "hunted_mansion"));
    public static final ResourceKey<DimensionType> HUNTED_MANSION_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PlayerMod.MODID, "hunted_mansion_type"));

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(WONDERLANDS_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));

        context.register(HUNTED_MANSION_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.VOID_BIOME)),
                noiseGenSettings.getOrThrow(ModNoiseSettings.VOID_NOISE));

        LevelStem wonderlandsStem = new LevelStem(dimTypes.getOrThrow(ModDimensions.WONDERLANDS_DIM_TYPE), wrappedChunkGenerator);

        context.register(WONDERLANDS_KEY, wonderlandsStem);

        LevelStem huntedMansionStem = new LevelStem(dimTypes.getOrThrow(ModDimensions.HUNTED_MANSION_DIM_TYPE), wrappedChunkGenerator);

        context.register(HUNTED_MANSION_KEY, huntedMansionStem);
    }

}
