package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.BerserkerBlockEntity;
import net.h8sh.playermod.block.entity.profession.DruidBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DruidBlockRenderer extends GeoBlockRenderer<DruidBlockEntity> {
    public DruidBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new DruidBlockModel());
    }
}
