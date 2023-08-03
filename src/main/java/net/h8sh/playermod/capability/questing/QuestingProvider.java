package net.h8sh.playermod.capability.questing;

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

public class QuestingProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Questing> QUESTING = CapabilityManager.get(new CapabilityToken<Questing>() {});

    private Questing questing = null;

    private final LazyOptional<Questing> optional = LazyOptional.of(this::createQuesting);

    private Questing createQuesting() {
        if (this.questing == null){
            this.questing = new Questing();
        }

        return this.questing;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == QUESTING) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createQuesting().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createQuesting().loadNBTData(nbt);
    }
}
