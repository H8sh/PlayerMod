package net.h8sh.playermod.block.entity.client;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.PaladinLecternEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PaladinLecternModel extends GeoModel<PaladinLecternEntity> {

    @Override
    public ResourceLocation getModelResource(PaladinLecternEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/paladin_lectern.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PaladinLecternEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/paladin_lectern_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PaladinLecternEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/paladin_lectern.animation.json");
    }
}
