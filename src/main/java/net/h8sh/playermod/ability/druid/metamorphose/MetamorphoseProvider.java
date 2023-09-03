package net.h8sh.playermod.ability.druid.metamorphose;

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

public class MetamorphoseProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Metamorphose> METAMORPHOSE = CapabilityManager.get(new CapabilityToken<Metamorphose>() {});

    private Metamorphose metamorphose = null;

    private final LazyOptional<Metamorphose> optional = LazyOptional.of(this::createMetamorphose);

    private Metamorphose createMetamorphose() {
        if (this.metamorphose == null){
            this.metamorphose = new Metamorphose();
        }

        return this.metamorphose;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == METAMORPHOSE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMetamorphose().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMetamorphose().loadNBTData(nbt);
    }
}
