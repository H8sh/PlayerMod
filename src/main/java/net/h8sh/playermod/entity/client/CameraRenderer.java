package net.h8sh.playermod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.CameraEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CameraRenderer extends MobRenderer<CameraEntity, CameraModel<CameraEntity>> {


    public CameraRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CameraModel<>(pContext.bakeLayer(ModModelLayers.CAMERA_LAYER)), 0);
    }

    @Override
    public ResourceLocation getTextureLocation(CameraEntity pEntity) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/camera_texture.png");
    }

    @Override
    public void render(CameraEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }


}
