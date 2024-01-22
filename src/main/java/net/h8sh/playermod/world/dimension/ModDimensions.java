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
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ModDimensions {

    //Citadel: ---------------------------------------------------------------------------------------------------------
    public static final ResourceKey<LevelStem> CITADEL_MIDDLE_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PlayerMod.MODID, "citadel_middle"));
    public static final ResourceKey<Level> CITADEL_MIDDLE_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "citadel_middle"));
    public static final ResourceKey<DimensionType> CITADEL_MIDDLE_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PlayerMod.MODID, "citadel_middle_type"));

    //Mine: ------------------------------------------------------------------------------------------------------------
    public static final ResourceKey<LevelStem> MINE_MIDDLE_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PlayerMod.MODID, "mine_middle"));
    public static final ResourceKey<Level> MINE_MIDDLE_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "mine_middle"));
    public static final ResourceKey<DimensionType> MINE_MIDDLE_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PlayerMod.MODID, "mine_middle_type"));

    //Fields: ----------------------------------------------------------------------------------------------------------
    public static final ResourceKey<LevelStem> FIELDS_MIDDLE_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PlayerMod.MODID, "fields_middle"));
    public static final ResourceKey<Level> FIELDS_MIDDLE_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "fields_middle"));
    public static final ResourceKey<DimensionType> FIELDS_MIDDLE_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PlayerMod.MODID, "fields_middle_type"));

    //Wonderlands: -----------------------------------------------------------------------------------------------------
    public static final ResourceKey<LevelStem> WONDERLANDS_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PlayerMod.MODID, "wonderlands"));
    public static final ResourceKey<Level> WONDERLANDS_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "wonderlands"));
    public static final ResourceKey<DimensionType> WONDERLANDS_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PlayerMod.MODID, "wonderlands_type"));

    //Mansion: ---------------------------------------------------------------------------------------------------------
    public static final ResourceKey<LevelStem> HUNTED_MANSION_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PlayerMod.MODID, "hunted_mansion"));
    public static final ResourceKey<Level> HUNTED_MANSION_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PlayerMod.MODID, "hunted_mansion"));
    public static final ResourceKey<DimensionType> HUNTED_MANSION_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PlayerMod.MODID, "hunted_mansion_type"));


    public static void bootstrapTypeInitializer(BootstapContext<DimensionType> context, ResourceKey<DimensionType> dimensionTypeResourceKey, int fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean bedWorks, boolean respawnAnchorWorks, int minY, int height, int logicalHeight, TagKey<Block> tagKey, ResourceLocation builtinDimensionTypes, float ambientLight, DimensionType.MonsterSettings monsterSettings) {
        context.register(dimensionTypeResourceKey, new DimensionType(
                OptionalLong.of(fixedTime), // fixedTime
                hasSkylight, // hasSkylight
                hasCeiling, // hasCeiling
                ultraWarm, // ultraWarm
                natural, // natural
                coordinateScale, // coordinateScale
                bedWorks, // bedWorks
                respawnAnchorWorks, // respawnAnchorWorks
                minY, // minY
                height, // height
                logicalHeight, // logicalHeight
                tagKey, // infiniburn
                builtinDimensionTypes, // effectsLocation
                ambientLight, // ambientLight
                monsterSettings));
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        bootstrapTypeInitializer(context, WONDERLANDS_DIM_TYPE, 12000, true, false, false, false, 1.0, true, false, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 1.0f, new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0));

        bootstrapTypeInitializer(context, HUNTED_MANSION_DIM_TYPE, 12000, true, false, false, false, 1.0, true, false, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 1.0f, new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0));

        bootstrapTypeInitializer(context, CITADEL_MIDDLE_DIM_TYPE, 12000, true, false, false, false, 1.0, true, false, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 1.0f, new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0));

        bootstrapTypeInitializer(context, MINE_MIDDLE_DIM_TYPE, 12000, true, false, false, false, 1.0, true, false, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 1.0f, new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0));

        bootstrapTypeInitializer(context, FIELDS_MIDDLE_DIM_TYPE, 12000, true, false, false, false, 1.0, true, false, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 1.0f, new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0));

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

        LevelStem citadelMiddleStem = new LevelStem(dimTypes.getOrThrow(ModDimensions.HUNTED_MANSION_DIM_TYPE), wrappedChunkGenerator);

        context.register(CITADEL_MIDDLE_KEY, citadelMiddleStem);

        LevelStem mineMiddleStem = new LevelStem(dimTypes.getOrThrow(ModDimensions.HUNTED_MANSION_DIM_TYPE), wrappedChunkGenerator);

        context.register(MINE_MIDDLE_KEY, mineMiddleStem);

        LevelStem fieldsMiddleStem = new LevelStem(dimTypes.getOrThrow(ModDimensions.HUNTED_MANSION_DIM_TYPE), wrappedChunkGenerator);

        context.register(FIELDS_MIDDLE_KEY, fieldsMiddleStem);
    }

}
