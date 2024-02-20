package net.h8sh.playermod.mixin;

import com.google.common.collect.Lists;
import net.h8sh.playermod.animation.ModAnimationStates;
import net.h8sh.playermod.animation.animations.AnimationManager;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.event.ClientEvents;
import net.h8sh.playermod.item.ModItems;
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
import net.minecraft.world.item.Items;
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
    protected static EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND;
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

    @Inject(method = "isScoping", at = @At("HEAD"), cancellable = true)
    public void isScoping(CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        cir.setReturnValue(this.isUsingItem() && (this.getUseItem().is(Items.SPYGLASS) || this.getUseItem().is(ModItems.SNIPER_ITEM.get())));
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

            animAquaMeta(player);
            animDruid(player);
            animInvocator(player);
            animFireMeta(player);
            animRogue(player);
            animSpiritusMeta(player);
            animWindMeta(player);
            animSteve(player);
            animWizard(player);
            animPaladin(player);
            animGunslinger(player);
            animMechanic(player);
        }

    }

    private void animMechanic(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.MECHANIC) {
            ModAnimationStates.MECHANIC_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.MECHANIC_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_FALL.animateWhen(player.fallDistance > AnimationManager.MECHANIC_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.MECHANIC_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.MECHANIC_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.MECHANIC_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animGunslinger(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.GUNSLINGER) {
            ModAnimationStates.GUNSLINGER_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.GUNSLINGER_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_FALL.animateWhen(player.fallDistance > AnimationManager.GUNSLINGER_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.GUNSLINGER_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.GUNSLINGER_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.GUNSLINGER_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animInvocator(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.INVOCATOR) {
            ModAnimationStates.INVOCATOR_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.INVOCATOR_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_FALL.animateWhen(player.fallDistance > AnimationManager.INVOCATOR_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.INVOCATOR_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.INVOCATOR_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.INVOCATOR_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animRogue(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.ROGUE) {
            ModAnimationStates.ROGUE_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.ROGUE_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_FALL.animateWhen(player.fallDistance > AnimationManager.ROGUE_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.ROGUE_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.ROGUE_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.ROGUE_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animSpiritusMeta(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.SPIRITUSMETA) {
            ModAnimationStates.SPIRITUSMETA_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.SPIRITUSMETA_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_FALL.animateWhen(player.fallDistance > AnimationManager.SPIRITUSMETA_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.SPIRITUSMETA_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.SPIRITUSMETA_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.SPIRITUSMETA_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animWindMeta(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.WINDMETA) {
            ModAnimationStates.WINDMETA_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.WINDMETA_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_FALL.animateWhen(player.fallDistance > AnimationManager.WINDMETA_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WINDMETA_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.WINDMETA_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WINDMETA_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animAquaMeta(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.AQUAMETA) {
            ModAnimationStates.AQUAMETA_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.AQUAMETA_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_FALL.animateWhen(player.fallDistance > AnimationManager.AQUAMETA_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.AQUAMETA_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.AQUAMETA_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.AQUAMETA_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animDruid(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.DRUID) {
            ModAnimationStates.DRUID_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.DRUID_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_FALL.animateWhen(player.fallDistance > AnimationManager.DRUID_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.DRUID_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.DRUID_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.DRUID_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animFireMeta(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.FIREMETA) {
            ModAnimationStates.FIREMETA_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.FIREMETA_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_FALL.animateWhen(player.fallDistance > AnimationManager.FIREMETA_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.FIREMETA_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.FIREMETA_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.FIREMETA_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animWizard(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.WIZARD) {
            ModAnimationStates.WIZARD_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.WIZARD_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_FALL.animateWhen(player.fallDistance > AnimationManager.WIZARD_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.WIZARD_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.WIZARD_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.WIZARD_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animPaladin(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.PALADIN) {
            ModAnimationStates.PALADIN_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.PALADIN_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_FALL.animateWhen(player.fallDistance > AnimationManager.PALADIN_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.PALADIN_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.PALADIN_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.PALADIN_IDLE_SHIFT_DOWN_TICK_COUNT);
        }
    }

    private void animSteve(Player player) {
        if (!this.level().isClientSide && Profession.getProfession() == Profession.Professions.BASIC) {
            ModAnimationStates.STEVE_IDLE.animateWhen(!player.walkAnimation.isMoving() && !player.isDeadOrDying() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerJump(), this.tickCount);
            ModAnimationStates.STEVE_BACK_DASH.animateWhen(AnimationHandler.getPlayerBackDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_FRONT_DASH.animateWhen(AnimationHandler.getPlayerFrontDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_RIGHT_DASH.animateWhen(AnimationHandler.getPlayerRightDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_LEFT_DASH.animateWhen(AnimationHandler.getPlayerLeftDash(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_DEATH.animateWhen(player.isDeadOrDying(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_JUMP.animateWhen(AnimationHandler.getPlayerJump(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_ATTACK.animateWhen(AnimationHandler.getPlayerAttack(), AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_FALL.animateWhen(player.fallDistance > AnimationManager.STEVE_FALL_FLAG, AnimationHandler.getCountTickAnimation());
            ModAnimationStates.STEVE_IDLE_SHIFT_DOWN.animateWhen(AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerShiftDown(), AnimationHandler.getCountTickAnimation());

        }

        if (AnimationHandler.getPlayerBackDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_BACK_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerAttack()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_ATTACK_ANIMATION_TICK_COUNT);
        }
        if (player.isDeadOrDying()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_DEATH_ANIMATION_TICK_COUNT);
        }
        if (player.fallDistance > AnimationManager.STEVE_FALL_FLAG) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_FALL_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerJump()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_JUMP_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerFrontDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_FRONT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerRightDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_RIGHT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerLeftDash()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_LEFT_DASH_ANIMATION_TICK_COUNT);
        }
        if (AnimationHandler.getPlayerIdleShiftDown()) {
            AnimationHandler.setAnimationLongInTick(AnimationManager.STEVE_IDLE_SHIFT_DOWN_TICK_COUNT);
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
