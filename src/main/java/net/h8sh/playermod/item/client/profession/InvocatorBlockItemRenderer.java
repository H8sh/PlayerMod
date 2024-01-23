package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.BerserkerBlockItem;
import net.h8sh.playermod.item.custom.profession.InvocatorBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class InvocatorBlockItemRenderer extends GeoItemRenderer<InvocatorBlockItem> {
    public InvocatorBlockItemRenderer() {
        super(new InvocatorBlockItemModel());
        withScale(0.1F);
    }
}
