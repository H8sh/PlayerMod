package net.h8sh.playermod.capability.riding;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RidingProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Riding> RIDING = CapabilityManager.get(new CapabilityToken<Riding>() {});

    private Riding riding = null;

    private final LazyOptional<Riding> optional = LazyOptional.of(this::createRiding);

    private Riding createRiding() {
        if (this.riding == null){
            this.riding = new Riding();
        }

        return this.riding;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == RIDING) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createRiding().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createRiding().loadNBTData(nbt);
    }
}
