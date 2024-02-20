package net.h8sh.playermod.entity.client.pnj;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.pnj.PnjEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PnjEntityModel extends GeoModel<PnjEntity> {

    @Override
    public ResourceLocation getModelResource(PnjEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/pnj_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PnjEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/pnj_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PnjEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/pnj_block.animation.json");
    }

}
