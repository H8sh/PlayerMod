package net.h8sh.playermod.mixin;

import com.google.common.collect.Lists;
import net.h8sh.playermod.animation.ModAnimationStates;
import net.h8sh.playermod.animation.animations.AnimationManager;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.event.ClientEvents;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    @Final
    @Shadow
    private static EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND;
    @Mutable
    @Final
    @Shadow
    private final Abilities abilities;
    @Shadow
    public float oBob;
    @Shadow
    public float bob;
    @Shadow
    protected int jumpTriggerTime;
    @Shadow
    protected FoodData foodData;
    @Final
    @Shadow
    private Inventory inventory;

    protected MixinPlayer(EntityType<? extends LivingEntity> pEntityType, Level pLevel, Abilities abilities) {
        super(pEntityType, pLevel);
        this.abilities = abilities;
    }

    @Inject(method = "aiStep",
            at = @At("HEAD"), cancellable = true)
    public void aiStep(CallbackInfo ci) {
        ci.cancel();

        if (this.jumpTriggerTime > 0) {
            --this.jumpTriggerTime;
        }

        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) {
                this.heal(1.0F);
            }

            if (this.foodData.needsFood() && this.tickCount % 10 == 0) {
                this.foodData.setFoodLevel(this.foodData.getFoodLevel() + 1);
            }
        }

        this.inventory.tick();
        this.oBob = this.bob;
        super.aiStep();
        this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
        float f;
        if (this.onGround() && !this.isDeadOrDying() && !this.isSwimming()) {
            f = Math.min(0.1F, (float) this.getDeltaMovement().horizontalDistance());
        } else {
            f = 0.0F;
        }

        this.bob += (f - this.bob) * 0.4F;
        if (this.getHealth() > 0.0F && !this.isSpectator()) {
            AABB aabb;
            if (this.isPassenger() && !this.getVehicle().isRemoved()) {
                aabb = this.getBoundingBox().minmax(this.getVehicle().getBoundingBox()).inflate(1.0D, 0.0D, 1.0D);
            } else {
                aabb = this.getBoundingBox().inflate(1.0D, 0.5D, 1.0D);
            }

            List<Entity> list = this.level().getEntities(this, aabb);
            List<Entity> list1 = Lists.newArrayList();

            for (int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                if (entity.getType() == EntityType.EXPERIENCE_ORB) {
                    list1.add(entity);
                } else if (!entity.isRemoved()) {
                    this.touch(entity);
                }
            }

            if (!list1.isEmpty()) {
                this.touch(Util.getRandom(list1, this.random));
            }
        }
    }

    @Shadow
    protected abstract void touch(Entity pEntity);

    @Inject(method = "startAutoSpinAttack",
            at = @At("HEAD"), cancellable = true)
    public void startAutoSpinAttack(int pAttackTicks, CallbackInfo ci) {
        ci.cancel();

        this.autoSpinAttackTicks = pAttackTicks;
        if (!this.level().isClientSide) {
            this.setLivingEntityFlag(4, true);
        }

    }

    @Inject(method = "hurt",
            at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();

        if (!net.minecraftforge.common.ForgeHooks.onPlayerAttack(this, pSource, pAmount)) cir.setReturnValue(false);
        if (this.isInvulnerableTo(pSource)) {
            cir.setReturnValue(false);
        } else if (this.abilities.invulnerable && !pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            cir.setReturnValue(false);
        } else {
            this.noActionTime = 0;
            if (this.isDeadOrDying()) {
                cir.setReturnValue(false);
            } else {

                if (pSource.scalesWithDifficulty()) {
                    if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
                        pAmount = 0.0F;
                    }

                    if (this.level().getDifficulty() == Difficulty.EASY) {
                        pAmount = Math.min(pAmount / 2.0F + 1.0F, pAmount);
                    }

                    if (this.level().getDifficulty() == Difficulty.HARD) {
                        pAmount = pAmount * 3.0F / 2.0F;
                    }
                }

                cir.setReturnValue(pAmount == 0.0F ? false : super.hurt(pSource, pAmount));
            }
        }

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
