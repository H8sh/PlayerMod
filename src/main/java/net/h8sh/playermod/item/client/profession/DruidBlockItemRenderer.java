package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.BerserkerBlockItem;
import net.h8sh.playermod.item.custom.profession.DruidBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DruidBlockItemRenderer extends GeoItemRenderer<DruidBlockItem> {
    public DruidBlockItemRenderer() {
        super(new DruidBlockItemModel());
        withScale(0.1F);
    }
}
