package net.h8sh.playermod.ability.rogue.teleportation;


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

public class TeleportationCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<TeleportationCapability> PLAYER_TELEPORTATION = CapabilityManager.get(new CapabilityToken<>() {
    });

    private TeleportationCapability playerTeleportation = null;

    private final LazyOptional<TeleportationCapability> optional = LazyOptional.of(this::createPlayerTeleportation);

    @NonNull
    private TeleportationCapability createPlayerTeleportation() {
        if (this.playerTeleportation == null) {
            this.playerTeleportation = new TeleportationCapability();
        }
        return this.playerTeleportation;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_TELEPORTATION) {
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
        createPlayerTeleportation().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerTeleportation().loadNBTData(nbt);
    }
}