package net.h8sh.playermod.mixin;

import net.h8sh.playermod.event.ClientEvents;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItem {

    @Inject(method = "getOrCreateDescriptionId",
            at = @At("HEAD"), cancellable = true)
    private void getOrCreateDescriptionId(CallbackInfoReturnable<String> cir) {
        boolean shouldRenderHotBar = ClientEvents.getHotBar();
        if (!shouldRenderHotBar) {
            cir.cancel();
            cir.setReturnValue("");
        }
    }
}
