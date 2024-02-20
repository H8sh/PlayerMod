package net.h8sh.playermod.entity.client.wonderlandshome;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.wonderlandshome.LivingLamppost;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LivingLamppostRenderer extends GeoEntityRenderer<LivingLamppost> {

    public LivingLamppostRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LivingLamppostModel());
    }

    @Override
    public ResourceLocation getTextureLocation(LivingLamppost animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/living_lamppost.png");
    }

    @Override
    public void render(LivingLamppost entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
