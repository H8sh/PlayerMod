package net.h8sh.playermod.ability.druid.aquameta.wateraoe;


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

public class WaterAoECapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<WaterAoECapability> PLAYER_WATERAOE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private WaterAoECapability playerWaterAoE = null;

    private final LazyOptional<WaterAoECapability> optional = LazyOptional.of(this::createPlayerWaterAoE);

    @NonNull
    private WaterAoECapability createPlayerWaterAoE() {
        if (this.playerWaterAoE == null) {
            this.playerWaterAoE = new WaterAoECapability();
        }
        return this.playerWaterAoE;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_WATERAOE) {
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
        createPlayerWaterAoE().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerWaterAoE().loadNBTData(nbt);
    }
}