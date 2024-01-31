package net.h8sh.playermod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.PetEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PetRenderer extends MobRenderer<PetEntity, PetModel<PetEntity>> {


    public PetRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PetModel<>(pContext.bakeLayer(ModModelLayers.CAMERA_LAYER)), 0);
    }

    @Override
    public ResourceLocation getTextureLocation(PetEntity pEntity) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/pet_texture.png");
    }

    @Override
    public void render(PetEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }


}
