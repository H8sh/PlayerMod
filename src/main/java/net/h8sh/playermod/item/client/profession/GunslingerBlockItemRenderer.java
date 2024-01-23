package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.BerserkerBlockItem;
import net.h8sh.playermod.item.custom.profession.GunslingerBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GunslingerBlockItemRenderer extends GeoItemRenderer<GunslingerBlockItem> {
    public GunslingerBlockItemRenderer() {
        super(new GunslingerBlockItemModel());
        withScale(0.1F);
    }
}
