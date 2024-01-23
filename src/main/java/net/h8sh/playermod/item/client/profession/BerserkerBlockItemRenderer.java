package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.BerserkerBlockItem;
import net.h8sh.playermod.item.custom.profession.PaladinBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BerserkerBlockItemRenderer extends GeoItemRenderer<BerserkerBlockItem> {
    public BerserkerBlockItemRenderer() {
        super(new BerserkerBlockItemModel());
        withScale(0.1F);
    }
}
