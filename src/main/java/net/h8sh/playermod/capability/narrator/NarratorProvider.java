package net.h8sh.playermod.capability.narrator;

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

public class NarratorProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Narrator> NARRATOR = CapabilityManager.get(new CapabilityToken<Narrator>() {});

    private Narrator narrator = null;

    private final LazyOptional<Narrator> optional = LazyOptional.of(this::createNarrator);

    private Narrator createNarrator() {
        if (this.narrator == null){
            this.narrator = new Narrator();
        }

        return this.narrator;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == NARRATOR) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createNarrator().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createNarrator().loadNBTData(nbt);
    }
}
