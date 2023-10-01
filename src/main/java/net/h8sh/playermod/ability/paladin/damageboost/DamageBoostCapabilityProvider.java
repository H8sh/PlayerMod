package net.h8sh.playermod.ability.paladin.damageboost;


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

public class DamageBoostCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<DamageBoostCapability> PLAYER_DAMAGEBOOST = CapabilityManager.get(new CapabilityToken<>() {
    });

    private DamageBoostCapability playerDamageBoost = null;

    private final LazyOptional<DamageBoostCapability> optional = LazyOptional.of(this::createPlayerDamageBoost);

    @NonNull
    private DamageBoostCapability createPlayerDamageBoost() {
        if (this.playerDamageBoost == null) {
            this.playerDamageBoost = new DamageBoostCapability();
        }
        return this.playerDamageBoost;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_DAMAGEBOOST) {
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
        createPlayerDamageBoost().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerDamageBoost().loadNBTData(nbt);
    }
}