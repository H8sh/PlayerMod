package net.h8sh.playermod.block.entity.client;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.AdamBlockEntity;
import net.h8sh.playermod.block.entity.PlinthBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PlinthBlockModel extends GeoModel<PlinthBlockEntity> {

    @Override
    public ResourceLocation getModelResource(PlinthBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/plinth_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PlinthBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/plinth_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PlinthBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/plinth_block.animation.json");
    }
}
