package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.narrator.NarratorProvider;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.SwouiffiEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID)
    public static class ForgeEvents {


        @SubscribeEvent
        public static void onAttachedCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(ProfessionProvider.PROFESSION).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "profession"), new ProfessionProvider());
                }
                if (!event.getObject().getCapability(NarratorProvider.NARRATOR).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "narrator"), new NarratorProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                event.getOriginal().reviveCaps();

                event.getOriginal().getCapability(NarratorProvider.NARRATOR).ifPresent(oldStore -> {
                    event.getEntity().getCapability(NarratorProvider.NARRATOR).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(ProfessionProvider.PROFESSION).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ProfessionProvider.PROFESSION).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                event.getOriginal().invalidateCaps();
            }
        }


    }

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntities.Swouiffi.get(), SwouiffiEntity.setAttributes());
        }

    }

}
