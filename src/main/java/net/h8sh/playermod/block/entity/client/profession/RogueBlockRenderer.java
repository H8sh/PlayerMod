package net.h8sh.playermod.block.entity.client.profession;

import net.h8sh.playermod.block.entity.profession.PaladinBlockEntity;
import net.h8sh.playermod.block.entity.profession.RogueBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RogueBlockRenderer extends GeoBlockRenderer<RogueBlockEntity> {
    public RogueBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new RogueBlockModel());
    }
}
