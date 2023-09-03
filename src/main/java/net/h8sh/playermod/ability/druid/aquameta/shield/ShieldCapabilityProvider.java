package net.h8sh.playermod.ability.druid.aquameta.shield;


import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
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

public class ShieldCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<SmokeCapability> PLAYER_SHIELD = CapabilityManager.get(new CapabilityToken<>() {
    });

    private SmokeCapability playerShield = null;

    private final LazyOptional<SmokeCapability> optional = LazyOptional.of(this::createPlayerShield);

    @NonNull
    private SmokeCapability createPlayerShield() {
        if (this.playerShield == null) {
            this.playerShield = new SmokeCapability();
        }
        return this.playerShield;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_SHIELD) {
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
        createPlayerShield().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerShield().loadNBTData(nbt);
    }
}