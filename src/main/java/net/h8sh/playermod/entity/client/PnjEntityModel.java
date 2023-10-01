package net.h8sh.playermod.entity.client;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.PnjEntity;
import net.h8sh.playermod.entity.custom.SwouiffiEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PnjEntityModel extends GeoModel<PnjEntity> {

    @Override
    public ResourceLocation getModelResource(PnjEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/pnj_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PnjEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/pnj_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PnjEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/pnj_block.animation.json");
    }

}
