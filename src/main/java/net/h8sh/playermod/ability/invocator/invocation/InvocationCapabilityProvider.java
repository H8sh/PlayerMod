package net.h8sh.playermod.ability.invocator.invocation;


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

public class InvocationCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<InvocationCapability> PLAYER_INVOCATION = CapabilityManager.get(new CapabilityToken<>() {
    });

    private InvocationCapability playerInvocation = null;

    private final LazyOptional<InvocationCapability> optional = LazyOptional.of(this::createPlayerInvocation);

    @NonNull
    private InvocationCapability createPlayerInvocation() {
        if (this.playerInvocation == null) {
            this.playerInvocation = new InvocationCapability();
        }
        return this.playerInvocation;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_INVOCATION) {
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
        createPlayerInvocation().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerInvocation().loadNBTData(nbt);
    }
}