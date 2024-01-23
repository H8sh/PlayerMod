package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.InvocatorBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class InvocatorBlockItemModel extends GeoModel<InvocatorBlockItem> {
    @Override
    public ResourceLocation getModelResource(InvocatorBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/invocator_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InvocatorBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/invocator_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InvocatorBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/invocator_block.animation.json");
    }
}
