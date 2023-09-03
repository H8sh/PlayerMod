package net.h8sh.playermod.networking.classes.wizard;

import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapabilityProvider;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AoEC2SPacket {

    public AoEC2SPacket() {
    }

    public AoEC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            if (MagicAoECapability.getPrepareAoe()) {
                player.getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).ifPresent(magicAoECapability -> {
                    magicAoECapability.spawnAoeMarker(level);
                    if (!KeyBinding.SECOND_SPELL_KEY.isDown() && !MagicAoECapability.getReadyToUse()) {
                        magicAoECapability.eraseOldAoe(level);
                        MagicAoECapability.setPrepareAoe(false);
                    }
                });
            }

        });

        return true;
    }

}
