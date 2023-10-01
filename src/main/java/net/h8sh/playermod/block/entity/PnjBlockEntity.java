package net.h8sh.playermod.block.entity;

import net.h8sh.playermod.event.ScreenEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PnjBlockEntity extends BlockEntity implements GeoBlockEntity {

    private static boolean isRightClicking = false;
    private static boolean isIdle = true;
    private static boolean pressCloseScreen = false;
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public PnjBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PNJ_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static void rightClicked() {
        isRightClicking = true;
    }

    public static void idle() {
        isIdle = true;
    }

    public static void cancelIdle() {
        isIdle = false;
    }

    public static void closeScreen() {
        pressCloseScreen = true;
    }

    public static void resetScreen() {
        pressCloseScreen = false;
    }

    public static void cancelRightClicked() {
        isRightClicking = false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if (isRightClicking && isIdle) {

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            executor.schedule(PnjBlockEntity::cancelRightClicked, 0, TimeUnit.SECONDS);

            executor.schedule(PnjBlockEntity::cancelIdle, 0, TimeUnit.SECONDS);

            executor.schedule(ScreenEvent::openPnjBlockScreen, 3, TimeUnit.SECONDS);

            executor.shutdown();


            return PlayState.CONTINUE;
        } else if (pressCloseScreen && !isIdle) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            resetScreen();
            idle();
            return PlayState.CONTINUE;
        }

        return PlayState.CONTINUE;

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
}
