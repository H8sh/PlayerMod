package net.h8sh.playermod.capability.reputation;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReputationProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Reputation> REPUTATION = CapabilityManager.get(new CapabilityToken<Reputation>() {});

    private Reputation reputation = null;

    private final LazyOptional<Reputation> optional = LazyOptional.of(this::createReputation);

    private Reputation createReputation() {
        if (this.reputation == null){
            this.reputation = new Reputation();
        }

        return this.reputation;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == REPUTATION) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createReputation().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createReputation().loadNBTData(nbt);
    }
}
