package net.h8sh.playermod.item;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.ModBlocks;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.item.custom.PaladinLecternItem;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PlayerMod.MODID);

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

    //Metamorphoses ------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> PALADIN_LECTERN_ITEM = ITEMS.register("paladin_lectern",
            () -> new PaladinLecternItem(ModBlocks.PALADIN_LECTERN.get(), new Item.Properties()));

    //TODO: replace item book by custom item book
    public static final RegistryObject<Item> PALADIN_BOOK = ITEMS.register("paladin_book",
            () -> new BookItem(new Item.Properties()));

    //TODO: replace item book by custom item book
    public static final RegistryObject<Item> WIZARD_BOOK = ITEMS.register("wizard_book",
            () -> new BookItem(new Item.Properties()));

    //TODO: replace item book by custom item book
    public static final RegistryObject<Item> DRUID_BOOK = ITEMS.register("druid_book",
            () -> new BookItem(new Item.Properties()));

    public static final RegistryObject<Item> BASIC_BOOK = ITEMS.register("basic_book",
            () -> new BookItem(new Item.Properties()));

    // Reputation Upgrade Skill ----------------------------------------------------------------------------------------
    public static final RegistryObject<Item> REPUTATION_POTION = ITEMS.register("reputation_potion",
            () -> new PotionItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
