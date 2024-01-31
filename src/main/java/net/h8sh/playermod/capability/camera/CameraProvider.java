package net.h8sh.playermod.capability.camera;

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

public class CameraProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<CameraManager> CAMERA = CapabilityManager.get(new CapabilityToken<CameraManager>() {});

    private CameraManager cameraManager = null;

    private final LazyOptional<CameraManager> optional = LazyOptional.of(this::createCamera);

    private CameraManager createCamera() {
        if (this.cameraManager == null){
            this.cameraManager = new CameraManager();
        }

        return this.cameraManager;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CAMERA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCamera().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCamera().loadNBTData(nbt);
    }
}
