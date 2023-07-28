package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.networking.AbilityMessages;
import net.h8sh.playermod.ability.networking.wizard.aoepacket.PacketMagicAoE;
import net.h8sh.playermod.ability.networking.wizard.manapacket.crystal.PacketKillAllCrystal;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoEManager;
import net.h8sh.playermod.ability.wizard.mana.ManaManager;
import net.h8sh.playermod.ability.wizard.mana.crystal.CrystalManager;
import net.h8sh.playermod.capability.ability.wizard.aoe.MagicAoECapabilityProvider;
import net.h8sh.playermod.capability.ability.wizard.mana.ManaCapabilityProvider;
import net.h8sh.playermod.capability.ability.wizard.mana.crystal.CrystalCapabilityProvider;
import net.h8sh.playermod.capability.narrator.NarratorProvider;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.h8sh.playermod.entity.custom.LivingLamppost;
import net.h8sh.playermod.entity.custom.SwouiffiEntity;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.geom.Point2D;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID)
    public static class ForgeEvents {

        //Capabilities

        private static final long EXECUTION_DELAY = 1000;
        private static long lastExecutionTime = 0;

        //TODO: on player death: kill every crystal + reset crystal data to 0

        @SubscribeEvent
        public static void onAttachedCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(ProfessionProvider.PROFESSION).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "profession"), new ProfessionProvider());
                }
                if (!event.getObject().getCapability(NarratorProvider.NARRATOR).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "narrator"), new NarratorProvider());
                }
                if (!event.getObject().getCapability(ManaCapabilityProvider.PLAYER_MANA).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "mana"), new ManaCapabilityProvider());
                }
                if (!event.getObject().getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "magic_aoe"), new MagicAoECapabilityProvider());
                }
                if (!event.getObject().getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "crystal"), new CrystalCapabilityProvider());
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
                event.getOriginal().getCapability(ManaCapabilityProvider.PLAYER_MANA).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ManaCapabilityProvider.PLAYER_MANA).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).ifPresent(oldStore -> {
                    event.getEntity().getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                event.getOriginal().invalidateCaps();

                //Crystal reset if player die
                AbilityMessages.sendToServer(new PacketKillAllCrystal());
            }
        }

        @SubscribeEvent
        public static void onWorldTick(TickEvent.LevelTickEvent event) {
            if (event.level.isClientSide()) {
                return;
            }
            if (event.phase == TickEvent.Phase.START) {
                return;
            }
            //-------------------------------------------------------------------------------------------------------

            //Wizard

            //Mana drain
            ManaManager manaManager = ManaManager.get(event.level);
            manaManager.tick(event.level);

            //Crystal data set
            CrystalManager crystalManager = CrystalManager.get(event.level);
            crystalManager.tick(event.level);

            //Magic AoE placement
            long currentTime = System.currentTimeMillis();

            if (KeyBinding.SECOND_SPELL_KEY.isDown()) {
                MagicAoEManager.tick(event.level);
            } else if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {
                if (currentTime - lastExecutionTime >= EXECUTION_DELAY) {
                    ModMessages.sendToServer(new PacketMagicAoE());
                    lastExecutionTime = currentTime;
                }
            }

            //--------------------------------------------------------------------------------------
        }


    }

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntities.SWOUIFFI.get(), SwouiffiEntity.setAttributes());
            event.put(ModEntities.LIVING_LAMPPOST.get(), LivingLamppost.setAttributes());
            event.put(ModEntities.CRYSTAL.get(), CrystalEntity.setAttributes());
        }

    }

}
