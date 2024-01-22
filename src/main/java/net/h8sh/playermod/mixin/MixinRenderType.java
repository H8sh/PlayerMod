package net.h8sh.playermod.mixin;

import net.h8sh.playermod.shader.ModRenderTypes;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderType.class)
public abstract class MixinRenderType {
    @Inject(method = "translucent",
            at = @At("HEAD"), cancellable = true)
    private static void translucent(CallbackInfoReturnable<RenderType> cir) {
        /*cir.cancel();
        cir.setReturnValue(ModRenderTypes.brightSolid());*/
    }
}
