package net.h8sh.playermod.item;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PlayerMod.MODID);

    public static final RegistryObject<Item> SWOUIFFI_SPAWN_EGG = ITEMS.register("swouiffi_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.Swouiffi, 0xD57E36, 0x1D0D00,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
