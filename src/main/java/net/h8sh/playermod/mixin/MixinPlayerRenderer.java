package net.h8sh.playermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.capability.profession.Profession;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    //Use to control the skin location, the hand animation and rendering

    private MixinPlayerRenderer(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadow) {
        super(context, model, shadow);
    }

    @Inject(
            method = {"Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;)V"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void onRenderArm(PoseStack p_117776_, MultiBufferSource p_117777_, int p_117778_, AbstractClientPlayer p_117779_, ModelPart p_117780_, ModelPart p_117781_, CallbackInfo ci) {
        //TODO: custom animation
    }


    @Inject(
            method = {"Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;"},
            at = {@At("RETURN")},
            cancellable = true
    )
    public void getTextureLocation(AbstractClientPlayer p_117783_, CallbackInfoReturnable<ResourceLocation> cir) {

        int currentProfession = Profession.getProfession();

        switch (currentProfession) {
            case 0:
                cir.setReturnValue(Profession.getProfessionTexture(Profession.getProfession()));
            case 1:
                cir.setReturnValue(Profession.getProfessionTexture(Profession.getProfession()));
            case 2:
                cir.setReturnValue(Profession.getProfessionTexture(Profession.getProfession()));
            case 3:
                cir.setReturnValue(Profession.getProfessionTexture(Profession.getProfession()));

        }
    }

}
