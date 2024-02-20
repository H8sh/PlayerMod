package net.h8sh.playermod.mixin;

import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class MixinAbstractClientPlayer {

    @Inject(method = "getFieldOfViewModifier", at = @At("HEAD"), cancellable = true)
    private void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
    /*    cir.cancel();
        cir.setReturnValue(1.2F);*/
    }

}
