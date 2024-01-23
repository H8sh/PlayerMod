package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.BerserkerBlockEntity;
import net.h8sh.playermod.block.entity.profession.InvocatorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class InvocatorBlockRenderer extends GeoBlockRenderer<InvocatorBlockEntity> {
    public InvocatorBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new InvocatorBlockModel());
    }
}
