package net.h8sh.playermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.client.pet.PetModel;
import net.h8sh.playermod.entity.client.ModModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParrotOnShoulderLayer.class)
public class MixinParrotOnShoulderLayer {

    private PetModel petModel;

    private static ResourceLocation CAMERA = new ResourceLocation(PlayerMod.MODID, "textures/entity/pet_texture.png");

    @Inject(method = "<init>", at = @At("TAIL"))
    private void parrotOnShoulderLayer(RenderLayerParent pRenderer, EntityModelSet pModelSet, CallbackInfo ci) {
        this.petModel = new PetModel(pModelSet.bakeLayer(ModModelLayers.CAMERA_LAYER));
    }

    @Inject(method = "Lnet/minecraft/client/renderer/entity/layers/ParrotOnShoulderLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/player/Player;FFFFZ)V", at = @At("HEAD"), cancellable = true)
    private void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Player pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pNetHeadYaw, float pHeadPitch, boolean pLeftShoulder, CallbackInfo ci) {
        ci.cancel();

        CompoundTag compoundtag = pLeftShoulder ? pLivingEntity.getShoulderEntityLeft() : pLivingEntity.getShoulderEntityRight();
        EntityType.byString(compoundtag.getString("id")).filter((p_117294_) -> {
            return p_117294_ == ModEntities.PET.get();
        }).ifPresent((p_262538_) -> {
            pPoseStack.pushPose();
            pPoseStack.translate(pLeftShoulder ? 0.4F : -0.4F, pLivingEntity.isCrouching() ? -1.3F : -1.5F, 0.0F);
            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.petModel.renderType(CAMERA));
            this.petModel.renderOnShoulder(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, pLivingEntity.tickCount);
            pPoseStack.popPose();
        });
    }


}
