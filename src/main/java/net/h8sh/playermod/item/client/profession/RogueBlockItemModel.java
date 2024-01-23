package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.RogueBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RogueBlockItemModel extends GeoModel<RogueBlockItem> {
    @Override
    public ResourceLocation getModelResource(RogueBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/rogue_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RogueBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/rogue_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RogueBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/rogue_block.animation.json");
    }
}
