package net.h8sh.playermod.entity.client.wizard;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.wizard.CrystalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CrystalModel extends GeoModel<CrystalEntity> {

    @Override
    public ResourceLocation getModelResource(CrystalEntity object) {
        return new ResourceLocation(PlayerMod.MODID, "geo/crystal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrystalEntity object) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/crystal_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrystalEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/crystal.animation.json");
    }
}