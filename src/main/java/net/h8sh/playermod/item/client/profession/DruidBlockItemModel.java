package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.DruidBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DruidBlockItemModel extends GeoModel<DruidBlockItem> {
    @Override
    public ResourceLocation getModelResource(DruidBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/druid_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DruidBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/druid_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DruidBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/druid_block.animation.json");
    }
}
