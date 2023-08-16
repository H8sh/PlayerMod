package net.h8sh.playermod.mixin;

import net.h8sh.playermod.event.ClientEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class MixinBlock {

    @Inject(method = "getDescriptionId",
            at = @At("HEAD"), cancellable = true)
    private void getDescriptionId(CallbackInfoReturnable<String> cir) {
        boolean shouldRenderHotBar = ClientEvents.getHotBar();
        if (!shouldRenderHotBar) {
            cir.cancel();
            cir.setReturnValue("");
        }
    }
}
