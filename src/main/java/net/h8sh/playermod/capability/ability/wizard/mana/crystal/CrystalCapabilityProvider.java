package net.h8sh.playermod.capability.ability.wizard.mana.crystal;

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

public class CrystalCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<CrystalCapability> PLAYER_CRYSTAL = CapabilityManager.get(new CapabilityToken<>() {
    });

    private CrystalCapability playerCrystal = null;

    private final LazyOptional<CrystalCapability> optional = LazyOptional.of(this::createPlayerCrystal);

    @NonNull
    private CrystalCapability createPlayerCrystal() {
        if (playerCrystal == null) {
            playerCrystal = new CrystalCapability();
        }
        return playerCrystal;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_CRYSTAL) {
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
        createPlayerCrystal().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCrystal().loadNBTData(nbt);
    }
}