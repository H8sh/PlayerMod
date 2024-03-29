package net.h8sh.playermod.ability.druid.firemeta.firescream;


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

public class FireScreamCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<FireScreamCapability> PLAYER_FIRESCREAM = CapabilityManager.get(new CapabilityToken<>() {
    });

    private FireScreamCapability playerFireScream = null;

    private final LazyOptional<FireScreamCapability> optional = LazyOptional.of(this::createPlayerFireScream);

    @NonNull
    private FireScreamCapability createPlayerFireScream() {
        if (this.playerFireScream == null) {
            this.playerFireScream = new FireScreamCapability();
        }
        return this.playerFireScream;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_FIRESCREAM) {
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
        createPlayerFireScream().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFireScream().loadNBTData(nbt);
    }
}