package net.h8sh.playermod.capability.travel;

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

public class TravelProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Travel> PLAYER_TRAVEL = CapabilityManager.get(new CapabilityToken<>() {
    });

    private Travel playerTravel = null;

    private final LazyOptional<Travel> optional = LazyOptional.of(this::createPlayerTravel);

    @NonNull
    private Travel createPlayerTravel() {
        if (this.playerTravel == null) {
            this.playerTravel = new Travel();
        }
        return this.playerTravel;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_TRAVEL) {
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
        createPlayerTravel().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerTravel().loadNBTData(nbt);
    }
}