package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.BerserkerBlockEntity;
import net.h8sh.playermod.block.entity.profession.MechanicBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MechanicBlockRenderer extends GeoBlockRenderer<MechanicBlockEntity> {
    public MechanicBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new MechanicBlockModel());
    }
}
