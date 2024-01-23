package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.WizardBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class WizardBlockRenderer extends GeoBlockRenderer<WizardBlockEntity> {
    public WizardBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new WizardBlockModel());
    }
}
