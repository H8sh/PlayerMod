package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.BerserkerBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BerserkerBlockItemModel extends GeoModel<BerserkerBlockItem> {
    @Override
    public ResourceLocation getModelResource(BerserkerBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/berserker_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BerserkerBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/berserker_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BerserkerBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/berserker_block.animation.json");
    }
}
