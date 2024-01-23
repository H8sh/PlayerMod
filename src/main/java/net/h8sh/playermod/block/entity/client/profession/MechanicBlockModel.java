package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.MechanicBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MechanicBlockModel extends GeoModel<MechanicBlockEntity> {

    @Override
    public ResourceLocation getModelResource(MechanicBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/mechanic_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MechanicBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/mechanic_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MechanicBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/mechanic_block.animation.json");
    }
}
