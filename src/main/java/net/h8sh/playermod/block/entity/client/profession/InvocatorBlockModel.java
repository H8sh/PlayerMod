package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.InvocatorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class InvocatorBlockModel extends GeoModel<InvocatorBlockEntity> {

    @Override
    public ResourceLocation getModelResource(InvocatorBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/invocator_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InvocatorBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/invocator_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InvocatorBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/invocator_block.animation.json");
    }
}
