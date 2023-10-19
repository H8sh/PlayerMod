package net.h8sh.playermod.animation.handler;


import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnimationProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<AnimationHandler> ANIMATION_HANDLER = CapabilityManager.get(new CapabilityToken<>() {
    });

    private AnimationHandler animationHandler = null;

    private final LazyOptional<AnimationHandler> optional = LazyOptional.of(this::createAnimationHandler);

    @NonNull
    private AnimationHandler createAnimationHandler() {
        if (this.animationHandler == null) {
            this.animationHandler = new AnimationHandler();
        }
        return this.animationHandler;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ANIMATION_HANDLER) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createAnimationHandler().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createAnimationHandler().loadNBTData(nbt);
    }
}