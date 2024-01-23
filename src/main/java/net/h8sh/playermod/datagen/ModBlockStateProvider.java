package net.h8sh.playermod.datagen;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PlayerMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        blockWithItem(ModBlocks.AOE_MARKER_BLOCK);
        blockWithItem(ModBlocks.TARGET_MARK_BLOCK);
        blockWithItem(ModBlocks.AOE_BLOCK);
        blockWithItem(ModBlocks.DAMAGE_SPELL_BLOCK);
        blockWithItem(ModBlocks.DEATH_BLOCK);
        blockWithItem(ModBlocks.CONSTRUCTION_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_CITADEL_NORTH_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_CITADEL_SOUTH_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_CITADEL_WEST_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_CITADEL_EAST_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_FIELDS_NORTH_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_FIELDS_SOUTH_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_FIELDS_WEST_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_FIELDS_EAST_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_MINE_NORTH_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_MINE_SOUTH_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_MINE_WEST_BLOCK);
        blockWithItem(ModBlocks.LINK_ZONE_MINE_EAST_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}