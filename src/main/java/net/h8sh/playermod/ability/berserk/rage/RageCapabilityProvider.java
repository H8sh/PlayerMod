package net.h8sh.playermod.ability.berserk.rage;


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

public class RageCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<RageCapability> PLAYER_RAGE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private RageCapability playerRage = null;

    private final LazyOptional<RageCapability> optional = LazyOptional.of(this::createPlayerRage);

    @NonNull
    private RageCapability createPlayerRage() {
        if (this.playerRage == null) {
            this.playerRage = new RageCapability();
        }
        return this.playerRage;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_RAGE) {
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
        createPlayerRage().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerRage().loadNBTData(nbt);
    }
}