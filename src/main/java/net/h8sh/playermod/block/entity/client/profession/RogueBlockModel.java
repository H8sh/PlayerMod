package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.PaladinBlockEntity;
import net.h8sh.playermod.block.entity.profession.RogueBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RogueBlockModel extends GeoModel<RogueBlockEntity> {

    @Override
    public ResourceLocation getModelResource(RogueBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/rogue_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RogueBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/rogue_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RogueBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/rogue_block.animation.json");
    }
}
