package net.h8sh.playermod.item.client;

import net.h8sh.playermod.item.custom.PaladinLecternItem;
import net.h8sh.playermod.item.custom.PnjBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PnjBlockItemRenderer extends GeoItemRenderer<PnjBlockItem> {
    public PnjBlockItemRenderer() {
        super(new PnjBlockItemModel());
    }
}
