package net.h8sh.playermod.item.client;

import net.h8sh.playermod.item.custom.PlinthBlockItem;
import net.h8sh.playermod.item.custom.PnjBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PlinthBlockItemRenderer extends GeoItemRenderer<PlinthBlockItem> {
    public PlinthBlockItemRenderer() {
        super(new PlinthBlockItemModel());
        withScale(0.1F);
    }
}
