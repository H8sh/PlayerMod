package net.h8sh.playermod.ability.druid.firemeta.fire_aura;


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

public class FireAuraCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<SmokeCapability> PLAYER_FIREAURA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private SmokeCapability playerFireAura = null;

    private final LazyOptional<SmokeCapability> optional = LazyOptional.of(this::createPlayerFireAura);

    @NonNull
    private SmokeCapability createPlayerFireAura() {
        if (this.playerFireAura == null) {
            this.playerFireAura = new SmokeCapability();
        }
        return this.playerFireAura;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_FIREAURA) {
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
        createPlayerFireAura().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFireAura().loadNBTData(nbt);
    }
}