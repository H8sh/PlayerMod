package net.h8sh.playermod.ability.invocator.assemble;


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

public class AssembleCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<AssembleCapability> PLAYER_ASSEMBLE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private AssembleCapability playerAssemble = null;

    private final LazyOptional<AssembleCapability> optional = LazyOptional.of(this::createPlayerAssemble);

    @NonNull
    private AssembleCapability createPlayerAssemble() {
        if (this.playerAssemble == null) {
            this.playerAssemble = new AssembleCapability();
        }
        return this.playerAssemble;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_ASSEMBLE) {
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
        createPlayerAssemble().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAssemble().loadNBTData(nbt);
    }
}