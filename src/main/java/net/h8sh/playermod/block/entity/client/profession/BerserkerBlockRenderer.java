package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.BerserkerBlockEntity;
import net.h8sh.playermod.block.entity.profession.PaladinBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BerserkerBlockRenderer extends GeoBlockRenderer<BerserkerBlockEntity> {
    public BerserkerBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new BerserkerBlockModel());
    }
}
