package net.h8sh.playermod.item;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PlayerMod.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SWOUIFFI_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.tutorial_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.SWOUIFFI_SPAWN_EGG.get());
                        pOutput.accept(ModItems.LIVING_LAMPPOST_SPAWN_EGG.get());
                        pOutput.accept(ModItems.CRYSTAL_SPAWN_EGG.get());

                        pOutput.accept(ModBlocks.PORTAL_BLOCK.get());
                        pOutput.accept(ModBlocks.DEATH_BLOCK.get());
                        pOutput.accept(ModBlocks.CONSTRUCTION_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_CITADEL_NORTH_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_CITADEL_SOUTH_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_CITADEL_WEST_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_CITADEL_EAST_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_MINE_NORTH_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_MINE_SOUTH_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_MINE_WEST_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_MINE_EAST_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_FIELDS_NORTH_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_FIELDS_SOUTH_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_FIELDS_WEST_BLOCK.get());
                        pOutput.accept(ModBlocks.LINK_ZONE_FIELDS_EAST_BLOCK.get());

                        pOutput.accept(ModItems.PNJ_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.ADAM_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.WIZARD_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.PALADIN_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.ROGUE_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.DRUID_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.INVOCATOR_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.GUNSLINGER_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.MECHANIC_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.BERSERKER_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.PLINTH_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.PALADIN_LECTERN_ITEM.get());
                        pOutput.accept(ModItems.SNIPER_ITEM.get());

                        pOutput.accept(ModItems.PALADIN_BOOK.get());
                        pOutput.accept(ModItems.DRUID_BOOK.get());
                        pOutput.accept(ModItems.WIZARD_BOOK.get());
                        pOutput.accept(ModItems.BASIC_BOOK.get());
                        pOutput.accept(ModItems.ROGUE_BOOK.get());
                        pOutput.accept(ModItems.BERSERK_BOOK.get());
                        pOutput.accept(ModItems.INVOCATOR_BOOK.get());
                        pOutput.accept(ModItems.FIREMETA_BOOK.get());
                        pOutput.accept(ModItems.AQUAMETA_BOOK.get());
                        pOutput.accept(ModItems.WINDMETA_BOOK.get());
                        pOutput.accept(ModItems.SPIRITUSMETA_BOOK.get());
                        pOutput.accept(ModItems.SOAP_WATER_BUCKET.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}