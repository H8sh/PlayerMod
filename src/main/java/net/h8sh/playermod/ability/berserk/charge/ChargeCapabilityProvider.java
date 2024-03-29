package net.h8sh.playermod.ability.berserk.charge;


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

public class ChargeCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ChargeCapability> PLAYER_CHARGE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private ChargeCapability playerCharge = null;

    private final LazyOptional<ChargeCapability> optional = LazyOptional.of(this::createPlayerCharge);

    @NonNull
    private ChargeCapability createPlayerCharge() {
        if (this.playerCharge == null) {
            this.playerCharge = new ChargeCapability();
        }
        return this.playerCharge;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_CHARGE) {
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
        createPlayerCharge().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCharge().loadNBTData(nbt);
    }
}