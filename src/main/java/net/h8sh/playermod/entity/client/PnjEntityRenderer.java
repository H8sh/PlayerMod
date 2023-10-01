package net.h8sh.playermod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.PnjEntity;
import net.h8sh.playermod.entity.custom.SwouiffiEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PnjEntityRenderer extends GeoEntityRenderer<PnjEntity> {

    public PnjEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PnjEntityModel());
    }

    @Override
    public ResourceLocation getTextureLocation(PnjEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/pnj_block_texture.png");
    }

    @Override
    public void render(PnjEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
