package net.h8sh.playermod.item.client.profession;

import net.h8sh.playermod.item.custom.profession.PaladinBlockItem;
import net.h8sh.playermod.item.custom.profession.RogueBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class RogueBlockItemRenderer extends GeoItemRenderer<RogueBlockItem> {
    public RogueBlockItemRenderer() {
        super(new RogueBlockItemModel());
        withScale(0.1F);
    }
}
