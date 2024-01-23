package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.BerserkerBlockEntity;
import net.h8sh.playermod.block.entity.profession.PaladinBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BerserkerBlockModel extends GeoModel<BerserkerBlockEntity> {

    @Override
    public ResourceLocation getModelResource(BerserkerBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/berserker_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BerserkerBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/berserker_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BerserkerBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/berserker_block.animation.json");
    }
}
