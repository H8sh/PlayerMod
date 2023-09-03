package net.h8sh.playermod.ability.wizard.freeze;


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

public class FreezeCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<FreezeCapability> PLAYER_FREEZE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private FreezeCapability playerFreeze = null;

    private final LazyOptional<FreezeCapability> optional = LazyOptional.of(this::createPlayerFreeze);

    @NonNull
    private FreezeCapability createPlayerFreeze() {
        if (this.playerFreeze == null) {
            this.playerFreeze = new FreezeCapability();
        }
        return this.playerFreeze;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_FREEZE) {
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
        createPlayerFreeze().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFreeze().loadNBTData(nbt);
    }
}