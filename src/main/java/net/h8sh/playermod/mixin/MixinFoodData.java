package net.h8sh.playermod.mixin;

import net.h8sh.playermod.capability.profession.Profession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class MixinFoodData {
    @Inject(
            method = {"tick"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void tick(Player pPlayer, CallbackInfo ci) {
        if (Profession.getProfession() != Profession.Professions.BASIC && Profession.getProfession() != null) {
            ci.cancel();
        }
    }
}
