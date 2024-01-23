package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.PaladinBlockItem;
import net.h8sh.playermod.item.custom.profession.WizardBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PaladinBlockItemModel extends GeoModel<PaladinBlockItem> {
    @Override
    public ResourceLocation getModelResource(PaladinBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/paladin_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PaladinBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/paladin_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PaladinBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/paladin_block.animation.json");
    }
}
