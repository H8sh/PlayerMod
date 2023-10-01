package net.h8sh.playermod.ability.paladin.armor;


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

public class ArmorCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ArmorCapability> PLAYER_ARMORBUFF = CapabilityManager.get(new CapabilityToken<>() {
    });

    private ArmorCapability playerArmorBuff = null;

    private final LazyOptional<ArmorCapability> optional = LazyOptional.of(this::createPlayerArmorBuff);

    @NonNull
    private ArmorCapability createPlayerArmorBuff() {
        if (this.playerArmorBuff == null) {
            this.playerArmorBuff = new ArmorCapability();
        }
        return this.playerArmorBuff;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_ARMORBUFF) {
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
        createPlayerArmorBuff().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerArmorBuff().loadNBTData(nbt);
    }
}