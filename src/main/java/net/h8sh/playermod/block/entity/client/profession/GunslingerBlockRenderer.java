package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.BerserkerBlockEntity;
import net.h8sh.playermod.block.entity.profession.GunslingerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GunslingerBlockRenderer extends GeoBlockRenderer<GunslingerBlockEntity> {
    public GunslingerBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new GunslingerBlockModel());
    }
}
