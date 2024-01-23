package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.PaladinBlockEntity;
import net.h8sh.playermod.block.entity.profession.WizardBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PaladinBlockModel extends GeoModel<PaladinBlockEntity> {

    @Override
    public ResourceLocation getModelResource(PaladinBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/paladin_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PaladinBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/paladin_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PaladinBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/paladin_block.animation.json");
    }
}
