package net.h8sh.playermod.block.entity;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.ModBlocks;
import net.h8sh.playermod.block.entity.profession.*;
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

    public static final RegistryObject<BlockEntityType<PlinthBlockEntity>> PLINTH_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("plinth_block_entity", ()->
                    BlockEntityType.Builder.of(PlinthBlockEntity::new,
                            ModBlocks.PLINTH_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<PnjBlockEntity>> PNJ_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("pnj_block_entity", ()->
                    BlockEntityType.Builder.of(PnjBlockEntity::new,
                            ModBlocks.PNJ_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<AdamBlockEntity>> ADAM_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("adam_block_entity", ()->
                    BlockEntityType.Builder.of(AdamBlockEntity::new,
                            ModBlocks.ADAM_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<WizardBlockEntity>> WIZARD_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("wizard_block_entity", ()->
                    BlockEntityType.Builder.of(WizardBlockEntity::new,
                            ModBlocks.WIZARD_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<PaladinBlockEntity>> PALADIN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("paladin_block_entity", ()->
                    BlockEntityType.Builder.of(PaladinBlockEntity::new,
                            ModBlocks.PALADIN_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<RogueBlockEntity>> ROGUE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("rogue_block_entity", ()->
                    BlockEntityType.Builder.of(RogueBlockEntity::new,
                            ModBlocks.ROGUE_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<BerserkerBlockEntity>> BERSERKER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("berserker_block_entity", ()->
                    BlockEntityType.Builder.of(BerserkerBlockEntity::new,
                            ModBlocks.BERSERKER_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<DruidBlockEntity>> DRUID_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("druid_block_entity", ()->
                    BlockEntityType.Builder.of(DruidBlockEntity::new,
                            ModBlocks.DRUID_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<InvocatorBlockEntity>> INVOCATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("invocator_block_entity", ()->
                    BlockEntityType.Builder.of(InvocatorBlockEntity::new,
                            ModBlocks.INVOCATOR_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<GunslingerBlockEntity>> GUNSLINGER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("gunslinger_block_entity", ()->
                    BlockEntityType.Builder.of(GunslingerBlockEntity::new,
                            ModBlocks.GUNSLINGER_BLOCK_ENTITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<MechanicBlockEntity>> MECHANIC_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("mechanic_block_entity", ()->
                    BlockEntityType.Builder.of(MechanicBlockEntity::new,
                            ModBlocks.MECHANIC_BLOCK_ENTITY.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
