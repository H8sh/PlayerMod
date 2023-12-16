package net.h8sh.playermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer {

    @Redirect(method = "setupRotations", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V", remap = false, ordinal = 1))
    protected void setupRotations(PoseStack instance, Quaternionf pQuaternion) {
    }

    @Inject(method = "getOverlayCoords", at = @At("HEAD"), cancellable = true)
    private static void getOverlayCoords(LivingEntity pLivingEntity, float pU, CallbackInfoReturnable<Integer> cir) {
        if (pLivingEntity instanceof Player) {
            cir.cancel();
            cir.setReturnValue(OverlayTexture.pack(OverlayTexture.u(pU), OverlayTexture.v(false)));
        }
    }
}
