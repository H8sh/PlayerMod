package net.h8sh.playermod.mixin;

import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.event.ClientEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer {

    @Inject(method = "getItemBySlot",
            at = @At("HEAD"), cancellable = true)
    private void getItemBySlot(EquipmentSlot pSlot, CallbackInfoReturnable<ItemStack> cir) {
        boolean shouldRenderHotBar = ClientEvents.getHotBar();
        if (!shouldRenderHotBar) {
            cir.cancel();
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    @Inject(method = "getHurtSound",
            at = @At("HEAD"), cancellable = true)
    private void getHurtSound(DamageSource pDamageSource, CallbackInfoReturnable<SoundEvent> cir) {
        cir.cancel();

        var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();

        if (currentProfession == Profession.Professions.BASIC) {
            cir.setReturnValue(pDamageSource.type().effects().sound());
        } else {
            //TODO
        }
    }

}
