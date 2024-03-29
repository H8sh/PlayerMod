package net.h8sh.playermod.entity.custom.wonderlandshome;

import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.awt.geom.Point2D;

public class LivingLamppost extends Animal implements GeoEntity {
    public static double distance;
    private boolean isLampOn = false;
    private boolean isAwake = false;
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public LivingLamppost(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6000.0D)
                .add(Attributes.FLYING_SPEED, (double) 0.4F)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.2F)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    @Override
    public void tick() {

        BlockPos playerPos = null;
        if (playerPos != null) {
            BlockPos livingLamppostPos = this.blockPosition();

            distance = Point2D.distance(playerPos.getX(), playerPos.getZ(), livingLamppostPos.getX(), livingLamppostPos.getZ());

            if (distance > 6) {
                setLampOff();
            } else if (distance <= 6 && distance > 3) {
                if (!isLampOn) {
                    setLampOn();
                }
                if (isAwake) {
                    setSleeping();
                }
            } else if (distance <= 3) {
                if (!isAwake) {
                    setAwake();
                }
            }
        }
    }

    private void setAwake() {
        isAwake = true;
    }

    private void setSleeping() {
        isAwake = false;
    }

    private void setLampOn() {
        isLampOn = true;
    }

    private void setLampOff() {
        isLampOn = false;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        /*if (distance > 6) {

            tAnimationState.getController().setAnimation(RawAnimation.begin().then("lamp_switch", Animation.LoopType.PLAY_ONCE).then("idle", Animation.LoopType.LOOP));
            this.playSound(SoundEvents.LANTERN_PLACE);
            return PlayState.CONTINUE;
        } else if (distance <= 6 && distance > 3) {
            if (!isLampOn) {

                tAnimationState.getController().setAnimation(RawAnimation.begin().then("lamp_switch", Animation.LoopType.PLAY_ONCE).then("idle", Animation.LoopType.LOOP));
                this.playSound(SoundEvents.LANTERN_PLACE);
                return PlayState.CONTINUE;
            }
            if (isAwake) {

                tAnimationState.getController().setAnimation(RawAnimation.begin().then("sleep", Animation.LoopType.PLAY_ONCE).then("idle", Animation.LoopType.LOOP));
                this.playSound(ModSounds.LIVING_LAMPPOST_MOVING.get());
                return PlayState.CONTINUE;
            }
        } else if (distance <= 3) {
            if (!isAwake) {

                tAnimationState.getController().setAnimation(RawAnimation.begin().then("wake_up", Animation.LoopType.PLAY_ONCE).then("awake", Animation.LoopType.LOOP));
                this.playSound(ModSounds.LIVING_LAMPPOST_MOVING.get());
                return PlayState.CONTINUE;
            }
        }
        return PlayState.CONTINUE;*/

        if (distance > 6) {

            tAnimationState.getController().setAnimation(RawAnimation.begin().then("lamp_switch", Animation.LoopType.PLAY_ONCE).then("idle", Animation.LoopType.LOOP));
            this.playSound(SoundEvents.LANTERN_PLACE);
            return PlayState.CONTINUE;
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.LIVING_LAMPPOST_IDLE.get();
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        LivingLamppost livingLamppost = ModEntities.LIVING_LAMPPOST.get().create(pLevel);
        return livingLamppost;
    }
}
