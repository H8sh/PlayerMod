package net.h8sh.playermod.block.entity;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PlayerMod.MODID);

    public static final RegistryObject<BlockEntityType<PaladinLecternEntity>> ANIMATED_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("animated_block_entity", ()->
                    BlockEntityType.Builder.of(PaladinLecternEntity::new,
                            ModBlocks.PALADIN_LECTERN.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
