package net.h8sh.playermod.entity;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.SwouiffiEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PlayerMod.MODID);

    public static final RegistryObject<EntityType<SwouiffiEntity>> Swouiffi =
            ENTITY_TYPES.register("swouiffi",
                    () -> EntityType.Builder.of(SwouiffiEntity::new, MobCategory.AMBIENT)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(PlayerMod.MODID, "swouiffi").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
