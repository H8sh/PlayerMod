package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.PaladinBlockItem;
import net.h8sh.playermod.item.custom.profession.WizardBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PaladinBlockItemRenderer extends GeoItemRenderer<PaladinBlockItem> {
    public PaladinBlockItemRenderer() {
        super(new PaladinBlockItemModel());
        withScale(0.1F);
    }
}
