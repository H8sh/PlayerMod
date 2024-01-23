package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.DruidBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DruidBlockModel extends GeoModel<DruidBlockEntity> {

    @Override
    public ResourceLocation getModelResource(DruidBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/druid_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DruidBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/druid_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DruidBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/druid_block.animation.json");
    }
}
