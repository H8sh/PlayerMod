package net.h8sh.playermod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrystalRenderer extends GeoEntityRenderer<CrystalEntity> {

    public CrystalRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrystalModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalEntity instance) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/crystal_texture.png");
    }

    @Override
    public void render(CrystalEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
