package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.packet.PlayerModelS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Method;

import static net.h8sh.playermod.mixin.MixinPlayerModel.createMesh;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachedCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(ProfessionProvider.PROFESSION).isPresent()) {
                event.addCapability(new ResourceLocation(PlayerMod.MODID, "properties"), new ProfessionProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(ProfessionProvider.PROFESSION).ifPresent(oldStore -> {
                event.getEntity().getCapability(ProfessionProvider.PROFESSION).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            event.player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {

            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                    ModMessages.sendToPlayer(new PlayerModelS2CPacket(profession.getProfession()), player);
                });
            }
        }
    }

}
