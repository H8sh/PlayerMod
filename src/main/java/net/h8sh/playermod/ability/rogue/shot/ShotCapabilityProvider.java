package net.h8sh.playermod.ability.rogue.shot;


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

public class ShotCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ShotCapability> PLAYER_SHOT = CapabilityManager.get(new CapabilityToken<>() {
    });

    private ShotCapability playerShot = null;

    private final LazyOptional<ShotCapability> optional = LazyOptional.of(this::createPlayerShot);

    @NonNull
    private ShotCapability createPlayerShot() {
        if (this.playerShot == null) {
            this.playerShot = new ShotCapability();
        }
        return this.playerShot;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_SHOT) {
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
        createPlayerShot().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerShot().loadNBTData(nbt);
    }
}