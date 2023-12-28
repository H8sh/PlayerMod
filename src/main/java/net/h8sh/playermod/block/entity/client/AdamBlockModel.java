package net.h8sh.playermod.block.entity.client;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.AdamBlockEntity;
import net.h8sh.playermod.block.entity.PnjBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AdamBlockModel extends GeoModel<AdamBlockEntity> {

    @Override
    public ResourceLocation getModelResource(AdamBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/pnj_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AdamBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/pnj_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AdamBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/pnj_block.animation.json");
    }
}
