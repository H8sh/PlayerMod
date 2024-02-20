package net.h8sh.playermod.entity.client.wonderlandshome;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.wonderlandshome.LivingLamppost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LivingLamppostModel extends GeoModel<LivingLamppost> {

    @Override
    public ResourceLocation getModelResource(LivingLamppost animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/living_lamppost.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LivingLamppost animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/living_lamppost.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LivingLamppost animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/living_lamppost.animation.json");
    }

}
