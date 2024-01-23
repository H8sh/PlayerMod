package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.PlinthBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PlinthBlockRenderer extends GeoBlockRenderer<PlinthBlockEntity> {
    public PlinthBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new PlinthBlockModel());
    }
}
