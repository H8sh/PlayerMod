package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CameraEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CAMERA.get(), CameraEntity.createAttribute().build());
    }

}
