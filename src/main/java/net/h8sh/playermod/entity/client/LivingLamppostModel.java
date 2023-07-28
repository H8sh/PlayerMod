package net.h8sh.playermod.entity.client;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.LivingLamppost;
import net.h8sh.playermod.entity.custom.SwouiffiEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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
