package net.h8sh.playermod.ability.paladin.heal;


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

public class HealCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<HealCapability> PLAYER_HEAL = CapabilityManager.get(new CapabilityToken<>() {
    });

    private HealCapability playerHeal = null;

    private final LazyOptional<HealCapability> optional = LazyOptional.of(this::createPlayerHeal);

    @NonNull
    private HealCapability createPlayerHeal() {
        if (this.playerHeal == null) {
            this.playerHeal = new HealCapability();
        }
        return this.playerHeal;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_HEAL) {
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
        createPlayerHeal().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerHeal().loadNBTData(nbt);
    }
}