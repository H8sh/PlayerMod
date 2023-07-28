package net.h8sh.playermod.item.client;

import net.h8sh.playermod.item.custom.PaladinLecternItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PaladinLecternItemRenderer extends GeoItemRenderer<PaladinLecternItem> {
    public PaladinLecternItemRenderer() {
        super(new PaladinLecternItemModel());
    }
}
