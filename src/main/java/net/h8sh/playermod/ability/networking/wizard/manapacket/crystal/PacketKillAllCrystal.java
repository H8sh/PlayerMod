package net.h8sh.playermod.ability.networking.wizard.manapacket.crystal;

import net.h8sh.playermod.ability.wizard.mana.crystal.CrystalManager;
import net.h8sh.playermod.capability.ability.wizard.mana.crystal.CrystalCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class PacketKillAllCrystal {

    public PacketKillAllCrystal() {
    }

    public PacketKillAllCrystal(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //server side
            ServerPlayer player = context.getSender();

            player.sendSystemMessage(Component.literal("kill all packet"));

            List<Entity> crystals = CrystalManager.removeLivingCrystals(player, player.level());

            for (Entity entity : crystals) {
                BlockPos pos = entity.blockPosition();
                player.getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).ifPresent(playerCrystal -> {
                    playerCrystal.removeCrystal(CrystalManager.get(player.level()).reduceCrystal(pos));
                });
                entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);

            }


        });

        return true;
    }

}