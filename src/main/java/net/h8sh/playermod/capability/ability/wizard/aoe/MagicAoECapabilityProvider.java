package net.h8sh.playermod.capability.ability.wizard.aoe;


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

public class MagicAoECapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<MagicAoECapability> PLAYER_MAGIC_AOE = CapabilityManager.get(new CapabilityToken<>() {
    });

    private MagicAoECapability playerMagicAoE = null;

    private final LazyOptional<MagicAoECapability> optional = LazyOptional.of(this::createPlayerMagicAoE);

    @NonNull
    private MagicAoECapability createPlayerMagicAoE() {
        if (this.playerMagicAoE == null) {
            this.playerMagicAoE = new MagicAoECapability();
        }
        return this.playerMagicAoE;
    }

    @NonNull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_MAGIC_AOE) {
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
        createPlayerMagicAoE().savedNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerMagicAoE().loadNBTData(nbt);
    }
}