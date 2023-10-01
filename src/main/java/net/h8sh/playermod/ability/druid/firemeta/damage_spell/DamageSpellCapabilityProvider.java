package net.h8sh.playermod.ability.druid.firemeta.damage_spell;


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

public class DamageSpellCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<DamageSpellCapability> PLAYER_DAMAGESPELL = CapabilityManager.get(new CapabilityToken<>() {
    });

    private DamageSpellCapability playerDamageSpell = null;

    private final LazyOptional<DamageSpellCapability> optional = LazyOptional.of(this::createPlayerDamageSpell);

    @NonNull
    private DamageSpellCapability createPlayerDamageSpell() {
        if (this.playerDamageSpell == null) {
            this.playerDamageSpell = new DamageSpellCapability();
        }
        return this.playerDamageSpell;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_DAMAGESPELL) {
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
        createPlayerDamageSpell().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerDamageSpell().loadNBTData(nbt);
    }
}