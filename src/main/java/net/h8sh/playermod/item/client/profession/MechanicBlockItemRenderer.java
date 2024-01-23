package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.BerserkerBlockItem;
import net.h8sh.playermod.item.custom.profession.MechanicBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MechanicBlockItemRenderer extends GeoItemRenderer<MechanicBlockItem> {
    public MechanicBlockItemRenderer() {
        super(new MechanicBlockItemModel());
        withScale(0.1F);
    }
}
