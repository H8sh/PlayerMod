package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.PlinthBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PlinthBlockItemRenderer extends GeoItemRenderer<PlinthBlockItem> {
    public PlinthBlockItemRenderer() {
        super(new PlinthBlockItemModel());
        withScale(0.1F);
    }
}
