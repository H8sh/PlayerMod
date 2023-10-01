package net.h8sh.playermod.ability.druid.spiritusmeta.aquaboost;


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

public class AquaBoostCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<AquaBoostCapability> PLAYER_AQUABOOST = CapabilityManager.get(new CapabilityToken<>() {
    });

    private AquaBoostCapability playerAquaBoost = null;

    private final LazyOptional<AquaBoostCapability> optional = LazyOptional.of(this::createPlayerAquaBoost);

    @NonNull
    private AquaBoostCapability createPlayerAquaBoost() {
        if (this.playerAquaBoost == null) {
            this.playerAquaBoost = new AquaBoostCapability();
        }
        return this.playerAquaBoost;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_AQUABOOST) {
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
        createPlayerAquaBoost().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAquaBoost().loadNBTData(nbt);
    }
}