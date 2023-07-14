package net.h8sh.playermod.capability.profession;

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

public class ProfessionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Profession> PROFESSION = CapabilityManager.get(new CapabilityToken<Profession>() {});

    private Profession profession = null;

    private final LazyOptional<Profession> optional = LazyOptional.of(this::createProfession);

    private Profession createProfession() {
        if (this.profession == null){
            this.profession = new Profession();
        }

        return this.profession;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PROFESSION) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createProfession().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createProfession().loadNBTData(nbt);
    }
}
