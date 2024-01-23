package net.h8sh.playermod.item;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.ModBlocks;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.fluid.ModFluids;
import net.h8sh.playermod.item.custom.PaladinLecternItem;
import net.h8sh.playermod.item.custom.profession.*;
import net.h8sh.playermod.item.custom.PnjBlockItem;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PlayerMod.MODID);

    public static final RegistryObject<Item> SOAP_WATER_BUCKET = ITEMS.register("soap_water_bucket",
            () -> new BucketItem(ModFluids.SOURCE_SOAP_WATER,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Entities ---------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> SWOUIFFI_SPAWN_EGG = ITEMS.register("swouiffi_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SWOUIFFI, 0xD57E36, 0x1D0D00,
                    new Item.Properties()));

    public static final RegistryObject<Item> LIVING_LAMPPOST_SPAWN_EGG = ITEMS.register("living_lamppost_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LIVING_LAMPPOST, 0x555555, 0xFFAA00,
                    new Item.Properties()));

    public static final RegistryObject<Item> CRYSTAL_SPAWN_EGG = ITEMS.register("crystal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRYSTAL, 0x555555, 0xFFAA00,
                    new Item.Properties()));

    //Professions ------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> PALADIN_LECTERN_ITEM = ITEMS.register("paladin_lectern",
            () -> new PaladinLecternItem(ModBlocks.PALADIN_LECTERN.get(), new Item.Properties()));

    public static final RegistryObject<Item> PNJ_BLOCK_ITEM = ITEMS.register("pnj_block",
            () -> new PnjBlockItem(ModBlocks.PNJ_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> ADAM_BLOCK_ITEM = ITEMS.register("adam_block",
            () -> new PnjBlockItem(ModBlocks.ADAM_BLOCK_ENTITY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLINTH_BLOCK_ITEM = ITEMS.register("plinth_block",
            () -> new PlinthBlockItem(ModBlocks.PLINTH_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> WIZARD_BLOCK_ITEM = ITEMS.register("wizard_block",
            () -> new WizardBlockItem(ModBlocks.WIZARD_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> PALADIN_BLOCK_ITEM = ITEMS.register("paladin_block",
            () -> new PaladinBlockItem(ModBlocks.PALADIN_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> ROGUE_BLOCK_ITEM = ITEMS.register("rogue_block",
            () -> new RogueBlockItem(ModBlocks.ROGUE_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> BERSERKER_BLOCK_ITEM = ITEMS.register("berserker_block",
            () -> new BerserkerBlockItem(ModBlocks.BERSERKER_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> DRUID_BLOCK_ITEM = ITEMS.register("druid_block",
            () -> new DruidBlockItem(ModBlocks.DRUID_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> INVOCATOR_BLOCK_ITEM = ITEMS.register("invocator_block",
            () -> new InvocatorBlockItem(ModBlocks.INVOCATOR_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> GUNSLINGER_BLOCK_ITEM = ITEMS.register("gunslinger_block",
            () -> new GunslingerBlockItem(ModBlocks.GUNSLINGER_BLOCK_ENTITY.get(), new Item.Properties()));

    public static final RegistryObject<Item> MECHANIC_BLOCK_ITEM = ITEMS.register("mechanic_block",
            () -> new MechanicBlockItem(ModBlocks.MECHANIC_BLOCK_ENTITY.get(), new Item.Properties()));

    //TODO: replace item book by custom item book
    public static final RegistryObject<Item> PALADIN_BOOK = ITEMS.register("paladin_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> WIZARD_BOOK = ITEMS.register("wizard_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> DRUID_BOOK = ITEMS.register("druid_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> BASIC_BOOK = ITEMS.register("basic_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> ROGUE_BOOK = ITEMS.register("rogue_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> BERSERK_BOOK = ITEMS.register("berserk_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> INVOCATOR_BOOK = ITEMS.register("invocator_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> FIREMETA_BOOK = ITEMS.register("firemeta_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> AQUAMETA_BOOK = ITEMS.register("aquameta_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> WINDMETA_BOOK = ITEMS.register("windmeta_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> SPIRITUSMETA_BOOK = ITEMS.register("spiritusmeta_book",
            () -> new BookItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
