package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.profession.WizardBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WizardBlockModel extends GeoModel<WizardBlockEntity> {

    @Override
    public ResourceLocation getModelResource(WizardBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/wizard_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WizardBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/wizard_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WizardBlockEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/wizard_block.animation.json");
    }
}
