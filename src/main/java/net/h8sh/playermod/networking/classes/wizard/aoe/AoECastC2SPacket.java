package net.h8sh.playermod.networking.classes.wizard.aoe;

import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapabilityProvider;
import net.h8sh.playermod.gui.AoEBarOverlay;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AoECastC2SPacket {

    public AoECastC2SPacket() {
    }

    public AoECastC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            if (MagicAoECapability.getReadyToUse()) {
                player.getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).ifPresent(magicAoECapability -> {
                    magicAoECapability.spawnAoe(level);
                    MagicAoECapability.setReadyToUse(false);
                    MagicAoECapability.setOnCD(true);
                    KeyBinding.SECOND_SPELL_KEY.setDown(false);
                });
            }

        });

        return true;
    }

}
