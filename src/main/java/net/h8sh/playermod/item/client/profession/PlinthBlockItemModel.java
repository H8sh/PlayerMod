package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.PlinthBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PlinthBlockItemModel extends GeoModel<PlinthBlockItem> {
    @Override
    public ResourceLocation getModelResource(PlinthBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/plinth_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PlinthBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/plinth_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PlinthBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/plinth_block.animation.json");
    }
}
