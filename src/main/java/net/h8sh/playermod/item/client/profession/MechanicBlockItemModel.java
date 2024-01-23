package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.MechanicBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MechanicBlockItemModel extends GeoModel<MechanicBlockItem> {
    @Override
    public ResourceLocation getModelResource(MechanicBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/mechanic_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MechanicBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/mechanic_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MechanicBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/mechanic_block.animation.json");
    }
}
