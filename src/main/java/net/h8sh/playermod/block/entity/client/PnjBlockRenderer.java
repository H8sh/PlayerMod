package net.h8sh.playermod.block.entity.client;

import net.h8sh.playermod.block.entity.PaladinLecternEntity;
import net.h8sh.playermod.block.entity.PnjBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PnjBlockRenderer extends GeoBlockRenderer<PnjBlockEntity> {
    public PnjBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new PnjBlockModel());
    }
}
