package net.h8sh.playermod.block;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.custom.*;
import net.h8sh.playermod.block.custom.linkzone.citadel.LinkZoneCitadelEastBlock;
import net.h8sh.playermod.block.custom.linkzone.citadel.LinkZoneCitadelNorthBlock;
import net.h8sh.playermod.block.custom.linkzone.citadel.LinkZoneCitadelSouthBlock;
import net.h8sh.playermod.block.custom.linkzone.citadel.LinkZoneCitadelWestBlock;
import net.h8sh.playermod.block.custom.linkzone.fields.LinkZoneFieldsEastBlock;
import net.h8sh.playermod.block.custom.linkzone.fields.LinkZoneFieldsNorthBlock;
import net.h8sh.playermod.block.custom.linkzone.fields.LinkZoneFieldsSouthBlock;
import net.h8sh.playermod.block.custom.linkzone.fields.LinkZoneFieldsWestBlock;
import net.h8sh.playermod.block.custom.linkzone.mine.LinkZoneMineEastBlock;
import net.h8sh.playermod.block.custom.linkzone.mine.LinkZoneMineNorthBlock;
import net.h8sh.playermod.block.custom.linkzone.mine.LinkZoneMineSouthBlock;
import net.h8sh.playermod.block.custom.linkzone.mine.LinkZoneMineWestBlock;
import net.h8sh.playermod.block.custom.profession.*;
import net.h8sh.playermod.fluid.ModFluids;
import net.h8sh.playermod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PlayerMod.MODID);

    public static final RegistryObject<LiquidBlock> SOAP_WATER_BLOCK = BLOCKS.register("soap_water_block",
            () -> new LiquidBlock(ModFluids.SOURCE_SOAP_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryObject<Block> PORTAL_BLOCK = registerBlock("portal",
            () -> new PortalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> CONSTRUCTION_BLOCK = registerBlock("construction_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> DEATH_BLOCK = registerBlock("death_block",
            () -> new DeathBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noLootTable().lightLevel((blockState) -> 15)));

    public static final RegistryObject<Block> SNIPER_BLOCK = registerBlock("sniper_block",
            () -> new SniperBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_CITADEL_NORTH_BLOCK = registerBlock("link_zone_citadel_north",
            () -> new LinkZoneCitadelNorthBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_CITADEL_SOUTH_BLOCK = registerBlock("link_zone_citadel_south",
            () -> new LinkZoneCitadelSouthBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_CITADEL_WEST_BLOCK = registerBlock("link_zone_citadel_west",
            () -> new LinkZoneCitadelWestBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_CITADEL_EAST_BLOCK = registerBlock("link_zone_citadel_east",
            () -> new LinkZoneCitadelEastBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_MINE_NORTH_BLOCK = registerBlock("link_zone_mine_north",
            () -> new LinkZoneMineNorthBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_MINE_SOUTH_BLOCK = registerBlock("link_zone_mine_south",
            () -> new LinkZoneMineSouthBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_MINE_WEST_BLOCK = registerBlock("link_zone_mine_west",
            () -> new LinkZoneMineWestBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_MINE_EAST_BLOCK = registerBlock("link_zone_mine_east",
            () -> new LinkZoneMineEastBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_FIELDS_NORTH_BLOCK = registerBlock("link_zone_fields_north",
            () -> new LinkZoneFieldsNorthBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_FIELDS_SOUTH_BLOCK = registerBlock("link_zone_fields_south",
            () -> new LinkZoneFieldsSouthBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_FIELDS_WEST_BLOCK = registerBlock("link_zone_fields_west",
            () -> new LinkZoneFieldsWestBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> LINK_ZONE_FIELDS_EAST_BLOCK = registerBlock("link_zone_fields_east",
            () -> new LinkZoneFieldsEastBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion().noCollission().noLootTable()));

    public static final RegistryObject<Block> PALADIN_LECTERN = BLOCKS.register("paladin_lectern",
            () -> new PaladinLectern(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion()));

    public static final RegistryObject<Block> PNJ_BLOCK_ENTITY = BLOCKS.register("pnj_block",
            () -> new PnjBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion()));

    public static final RegistryObject<Block> ADAM_BLOCK_ENTITY = BLOCKS.register("adam_block",
            () -> new AdamBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion()));

    public static final RegistryObject<Block> WIZARD_BLOCK_ENTITY = BLOCKS.register("wizard_block",
            () -> new WizardBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> PALADIN_BLOCK_ENTITY = BLOCKS.register("paladin_block",
            () -> new PaladinBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> ROGUE_BLOCK_ENTITY = BLOCKS.register("rogue_block",
            () -> new RogueBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> BERSERKER_BLOCK_ENTITY = BLOCKS.register("berserker_block",
            () -> new BerserkerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> DRUID_BLOCK_ENTITY = BLOCKS.register("druid_block",
            () -> new DruidBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> INVOCATOR_BLOCK_ENTITY = BLOCKS.register("invocator_block",
            () -> new InvocatorBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> GUNSLINGER_BLOCK_ENTITY = BLOCKS.register("gunslinger_block",
            () -> new GunslingerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> MECHANIC_BLOCK_ENTITY = BLOCKS.register("mechanic_block",
            () -> new MechanicBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> PLINTH_BLOCK = registerBlock("plinth",
            () -> new PlinthBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion()));


    //TODO: change to custom block
    public static final RegistryObject<Block> AOE_MARKER_BLOCK = BLOCKS.register("aoe_marker_block",
            () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.AIR).sound(SoundType.AMETHYST).noOcclusion()));

    //TODO: change to custom block
    public static final RegistryObject<Block> TARGET_MARK_BLOCK = BLOCKS.register("target_mark_marker_block",
            () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.AIR).sound(SoundType.AMETHYST).noOcclusion()));

    //TODO: change to custom block
    public static final RegistryObject<Block> AOE_BLOCK = BLOCKS.register("aoe_block",
            () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.AIR).sound(SoundType.AMETHYST).noOcclusion()));

    public static final RegistryObject<Block> DAMAGE_SPELL_BLOCK = BLOCKS.register("damage_spell_block",
            () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK).sound(SoundType.BONE_BLOCK).noOcclusion()));

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
