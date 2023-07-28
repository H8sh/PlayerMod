package net.h8sh.playermod.item.client;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.PaladinLecternItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PaladinLecternItemModel extends GeoModel<PaladinLecternItem> {
    @Override
    public ResourceLocation getModelResource(PaladinLecternItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/paladin_lectern.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PaladinLecternItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/paladin_lectern_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PaladinLecternItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/paladin_lectern.animation.json");
    }
}
