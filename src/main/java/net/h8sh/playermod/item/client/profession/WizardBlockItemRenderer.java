package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.PlinthBlockItem;
import net.h8sh.playermod.item.custom.profession.WizardBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WizardBlockItemRenderer extends GeoItemRenderer<WizardBlockItem> {
    public WizardBlockItemRenderer() {
        super(new WizardBlockItemModel());
        withScale(0.1F);
    }
}
