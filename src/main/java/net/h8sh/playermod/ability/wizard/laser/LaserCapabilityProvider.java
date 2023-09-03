package net.h8sh.playermod.ability.wizard.laser;


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

public class LaserCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<LaserCapability> PLAYER_LASER = CapabilityManager.get(new CapabilityToken<>() {
    });

    private LaserCapability playerLaser = null;

    private final LazyOptional<LaserCapability> optional = LazyOptional.of(this::createPlayerLaser);

    @NonNull
    private LaserCapability createPlayerLaser() {
        if (this.playerLaser == null) {
            this.playerLaser = new LaserCapability();
        }
        return this.playerLaser;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_LASER) {
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
        createPlayerLaser().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerLaser().loadNBTData(nbt);
    }
}