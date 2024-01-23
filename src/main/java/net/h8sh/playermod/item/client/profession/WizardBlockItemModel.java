package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.custom.profession.PlinthBlockItem;
import net.h8sh.playermod.item.custom.profession.WizardBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WizardBlockItemModel extends GeoModel<WizardBlockItem> {
    @Override
    public ResourceLocation getModelResource(WizardBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/wizard_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WizardBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/block/wizard_block_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WizardBlockItem animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/wizard_block.animation.json");
    }
}
