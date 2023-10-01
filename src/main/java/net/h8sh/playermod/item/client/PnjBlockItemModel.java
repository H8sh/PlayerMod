package net.h8sh.playermod.item.client;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.PnjBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PnjBlockItemModel extends GeoModel<PnjBlockItem> {
    @Override
    public ResourceLocation getModelResource(PnjBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/pnj_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PnjBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/pnj_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PnjBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/pnj_block.animation.json");
    }
}
