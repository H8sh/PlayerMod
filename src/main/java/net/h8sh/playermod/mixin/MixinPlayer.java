package net.h8sh.playermod.mixin;

import net.h8sh.playermod.animation.ModAnimationStates;
import net.h8sh.playermod.animation.animations.AnimationManager;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.event.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    @Final
    @Shadow
    private static EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND;

    @Final
    @Shadow
    private Inventory inventory;

    protected MixinPlayer(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Inject(method = "blockActionRestricted",
            at = @At("HEAD"), cancellable = true)
    private void blockActionRestricted(Level pLevel, BlockPos pPos, GameType pGameMode, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player.isDeadOrDying()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick",
            at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {

            Player player = Minecraft.getInstance().player;

            if (!this.level().isClientSide) {
                ModAnimationStates.STEVE_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getSteveAttack() && !AnimationHandler.getSteveShiftDown() && !AnimationHandler.getSteveJump(), this.tickCount);
                ModAnimationStates.STEVE_BACK_DASH.animateWhen(AnimationHandler.getSteveBackDash(), AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_FRONT_DASH.animateWhen(AnimationHandler.getSteveFrontDash(), AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_RIGHT_DASH.animateWhen(AnimationHandler.getSteveRightDash(), AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_LEFT_DASH.animateWhen(AnimationHandler.getSteveLeftDash(), AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_JUMP.animateWhen(AnimationHandler.getSteveJump(), AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_ATTACK.animateWhen(AnimationHandler.getSteveAttack(), AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_FALL.animateWhen(player.fallDistance > AnimationManager.STEVE_FALL_FLAG, AnimationHandler.getCountTickAnimation());
                ModAnimationStates.STEVE_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getSteveIdleShiftDown() && !AnimationHandler.getSteveShiftDown(), AnimationHandler.getCountTickAnimation());

            }

            if (AnimationHandler.getSteveBackDash()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_BACK_DASH_ANIMATION_TICK_COUNT);
            }
            if (AnimationHandler.getSteveAttack()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_ATTACK_ANIMATION_TICK_COUNT);
            }
            if (player.isDeadOrDying()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_DEATH_ANIMATION_TICK_COUNT);
            }
            if (player.fallDistance > AnimationManager.STEVE_FALL_FLAG) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_FALL_ANIMATION_TICK_COUNT);
            }
            if (AnimationHandler.getSteveJump()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_JUMP_ANIMATION_TICK_COUNT);
            }
            if (AnimationHandler.getSteveFrontDash()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_FRONT_DASH_ANIMATION_TICK_COUNT);
            }
            if (AnimationHandler.getSteveRightDash()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_RIGHT_DASH_ANIMATION_TICK_COUNT);
            }
            if (AnimationHandler.getSteveLeftDash()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_LEFT_DASH_ANIMATION_TICK_COUNT);
            }
            if (AnimationHandler.getSteveIdleShiftDown()) {
                AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_IDLE_SHIFT_DOWN_TICK_COUNT);
            }
        }

    }

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

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return this.inventory.armor;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        if (pSlot == EquipmentSlot.MAINHAND) {
            return this.inventory.getSelected();
        } else if (pSlot == EquipmentSlot.OFFHAND) {
            return this.inventory.offhand.get(0);
        } else {
            return pSlot.getType() == EquipmentSlot.Type.ARMOR ? this.inventory.armor.get(pSlot.getIndex()) : ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        this.verifyEquippedItem(pStack);
        if (pSlot == EquipmentSlot.MAINHAND) {
            this.onEquipItem(pSlot, this.inventory.items.set(this.inventory.selected, pStack), pStack);
        } else if (pSlot == EquipmentSlot.OFFHAND) {
            this.onEquipItem(pSlot, this.inventory.offhand.set(0, pStack), pStack);
        } else if (pSlot.getType() == EquipmentSlot.Type.ARMOR) {
            this.onEquipItem(pSlot, this.inventory.armor.set(pSlot.getIndex(), pStack), pStack);
        }
    }

    @Override
    public HumanoidArm getMainArm() {
        return this.entityData.get(DATA_PLAYER_MAIN_HAND) == 0 ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }
}
