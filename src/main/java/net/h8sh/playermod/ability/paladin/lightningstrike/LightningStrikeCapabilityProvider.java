package net.h8sh.playermod.ability.paladin.lightningstrike;


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

public class LightningStrikeCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<LightningStrikeCapability> PLAYER_LIGHTNINGSTRIKE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private LightningStrikeCapability playerLightningStrike = null;

    private final LazyOptional<LightningStrikeCapability> optional = LazyOptional.of(this::createPlayerLightningStrike);

    @NonNull
    private LightningStrikeCapability createPlayerLightningStrike() {
        if (this.playerLightningStrike == null) {
            this.playerLightningStrike = new LightningStrikeCapability();
        }
        return this.playerLightningStrike;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_LIGHTNINGSTRIKE) {
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
        createPlayerLightningStrike().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerLightningStrike().loadNBTData(nbt);
    }
}