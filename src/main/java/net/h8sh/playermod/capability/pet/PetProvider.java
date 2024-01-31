package net.h8sh.playermod.capability.pet;

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

public class PetProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Pet> PET = CapabilityManager.get(new CapabilityToken<Pet>() {});

    private Pet pet = null;

    private final LazyOptional<Pet> optional = LazyOptional.of(this::createPet);

    private Pet createPet() {
        if (this.pet == null){
            this.pet = new Pet();
        }

        return this.pet;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PET) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPet().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPet().loadNBTData(nbt);
    }
}
