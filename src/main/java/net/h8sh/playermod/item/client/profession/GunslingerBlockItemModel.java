package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.GunslingerBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GunslingerBlockItemModel extends GeoModel<GunslingerBlockItem> {
    @Override
    public ResourceLocation getModelResource(GunslingerBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/gunslinger_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GunslingerBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/gunslinger_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GunslingerBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/gunslinger_block.animation.json");
    }
}
