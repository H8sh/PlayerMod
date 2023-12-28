package net.h8sh.playermod.block.entity.client;

import net.h8sh.playermod.block.entity.AdamBlockEntity;
import net.h8sh.playermod.block.entity.PnjBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AdamBlockRenderer extends GeoBlockRenderer<AdamBlockEntity> {
    public AdamBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new AdamBlockModel());
    }
}
