package net.h8sh.playermod.ability.druid.spiritusmeta.windboost;


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

public class WindBoostCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<WindBoostCapability> PLAYER_WINDBOOST = CapabilityManager.get(new CapabilityToken<>() {
    });

    private WindBoostCapability playerWindBoost = null;

    private final LazyOptional<WindBoostCapability> optional = LazyOptional.of(this::createPlayerWindBoost);

    @NonNull
    private WindBoostCapability createPlayerWindBoost() {
        if (this.playerWindBoost == null) {
            this.playerWindBoost = new WindBoostCapability();
        }
        return this.playerWindBoost;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_WINDBOOST) {
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
        createPlayerWindBoost().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerWindBoost().loadNBTData(nbt);
    }
}