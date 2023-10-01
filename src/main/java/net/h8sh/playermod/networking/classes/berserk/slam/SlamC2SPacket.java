package net.h8sh.playermod.networking.classes.berserk.slam;

import net.h8sh.playermod.ability.berserk.rage.RageCapability;
import net.h8sh.playermod.ability.berserk.slam.SlamCapability;
import net.h8sh.playermod.ability.berserk.slam.SlamCapabilityProvider;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapabilityProvider;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SlamC2SPacket {

    public SlamC2SPacket() {
    }

    public SlamC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (!SlamCapability.getOnSlam())
            player.getCapability(SlamCapabilityProvider.PLAYER_SLAM).ifPresent(slam -> {
                SlamCapability.removeBlock(player);
                SlamCapability.setOnSlam(true);
            });
            if (RageCapability.getOnRage()) {
                player.getCapability(SlamCapabilityProvider.PLAYER_SLAM).ifPresent(slam -> {
                    SlamCapability.removeBlock(player);
                });
            }


        });

        return true;
    }

}
