package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.GunslingerBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GunslingerBlockModel extends GeoModel<GunslingerBlockEntity> {

    @Override
    public ResourceLocation getModelResource(GunslingerBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/gunslinger_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GunslingerBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/gunslinger_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GunslingerBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/gunslinger_block.animation.json");
    }
}
