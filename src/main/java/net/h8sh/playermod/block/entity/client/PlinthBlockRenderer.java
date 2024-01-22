package net.h8sh.playermod.block.entity.client;

import net.h8sh.playermod.block.entity.AdamBlockEntity;
import net.h8sh.playermod.block.entity.PlinthBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PlinthBlockRenderer extends GeoBlockRenderer<PlinthBlockEntity> {
    public PlinthBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new PlinthBlockModel());
    }
}
