package net.h8sh.playermod.block.entity.client;

import net.h8sh.playermod.block.entity.PaladinLecternEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PaladinLecternRenderer extends GeoBlockRenderer<PaladinLecternEntity> {


    public PaladinLecternRenderer(BlockEntityRendererProvider.Context context) {
        super(new PaladinLecternModel());
    }
}
