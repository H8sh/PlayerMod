package net.h8sh.playermod.entity;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.gunslinger.Sniper;
import net.h8sh.playermod.entity.custom.pet.PetEntity;
import net.h8sh.playermod.entity.custom.pet.SwouiffiEntity;
import net.h8sh.playermod.entity.custom.pnj.PnjEntity;
import net.h8sh.playermod.entity.custom.wizard.CrystalEntity;
import net.h8sh.playermod.entity.custom.wonderlandshome.LivingLamppost;
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

    public static final RegistryObject<EntityType<PnjEntity>> CUSTOM_PNJ =
            ENTITY_TYPES.register("pnj",
                    () -> EntityType.Builder.of(PnjEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(PlayerMod.MODID, "pnj").toString()));

    public static final RegistryObject<EntityType<SwouiffiEntity>> SWOUIFFI =
            ENTITY_TYPES.register("swouiffi",
                    () -> EntityType.Builder.of(SwouiffiEntity::new, MobCategory.AMBIENT)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(PlayerMod.MODID, "swouiffi").toString()));

    public static final RegistryObject<EntityType<LivingLamppost>> LIVING_LAMPPOST =
            ENTITY_TYPES.register("living_lamppost",
                    () -> EntityType.Builder.of(LivingLamppost::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(PlayerMod.MODID, "living_lamppost").toString()));

    public static final RegistryObject<EntityType<CrystalEntity>> CRYSTAL =
            ENTITY_TYPES.register("crystal",
                    () -> EntityType.Builder.of(CrystalEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(PlayerMod.MODID, "crystal").toString()));

    public static final RegistryObject<EntityType<PetEntity>> PET =
            ENTITY_TYPES.register("pet",
                    ()-> EntityType.Builder.of(PetEntity::new, MobCategory.CREATURE)
                            .sized(1f,1f)
                            .build("pet"));

    public static final RegistryObject<EntityType<Sniper>> SNIPER =
            ENTITY_TYPES.register("sniper",
                    ()-> EntityType.Builder.<Sniper>of(Sniper::new, MobCategory.CREATURE)
                            .sized(1f,1f)
                            .build("sniper"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
