package net.h8sh.playermod.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {

    @Inject(method = "handlePlayerCombatKill", at = @At("HEAD"), cancellable = true)
    private void handlePlayerCombatKill(ClientboundPlayerCombatKillPacket pPacket, CallbackInfo ci) {
        ci.cancel();
    }

}
