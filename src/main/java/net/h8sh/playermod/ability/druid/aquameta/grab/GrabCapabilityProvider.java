package net.h8sh.playermod.ability.druid.aquameta.grab;


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

public class GrabCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<GrabCapability> PLAYER_GRAB = CapabilityManager.get(new CapabilityToken<>() {
    });

    private GrabCapability playerGrab = null;

    private final LazyOptional<GrabCapability> optional = LazyOptional.of(this::createPlayerGrab);

    @NonNull
    private GrabCapability createPlayerGrab() {
        if (this.playerGrab == null) {
            this.playerGrab = new GrabCapability();
        }
        return this.playerGrab;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_GRAB) {
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
        createPlayerGrab().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerGrab().loadNBTData(nbt);
    }
}