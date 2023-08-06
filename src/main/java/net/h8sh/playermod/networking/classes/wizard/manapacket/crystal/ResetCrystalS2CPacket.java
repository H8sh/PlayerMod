package net.h8sh.playermod.networking.classes.wizard.manapacket.crystal;

import net.h8sh.playermod.ability.wizard.mana.crystal.CrystalManager;
import net.h8sh.playermod.capability.ability.wizard.mana.crystal.CrystalCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class ResetCrystalS2CPacket {

    private static BlockPos pos;

    public ResetCrystalS2CPacket(BlockPos pos) {
        this.pos = pos;
    }

    public ResetCrystalS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //server side
            ServerPlayer player = context.getSender();

            player.sendSystemMessage(Component.literal("reset packet"));

            player.getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).ifPresent(playerCrystal -> {
                playerCrystal.removeCrystal(CrystalManager.get(player.level()).reduceCrystal(pos));
            });


        });

        return true;
    }

}