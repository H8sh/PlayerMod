package net.h8sh.playermod.capability.camera;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class CameraManager {
    private static final Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
    private static boolean isVanillaCamera;
    private static int cameraX = 0;
    private static int cameraY = 0;
    private static int cameraZ = 0;

    public static boolean isVanillaCamera() {
        return isVanillaCamera;
    }

    public static int getCameraX() {
        return cameraX;
    }

    public static int getCameraY() {
        return cameraY;
    }

    public static int getCameraZ() {
        return cameraZ;
    }

    public void resetCameraVanilla() {
        isVanillaCamera = !isVanillaCamera;
    }

    public void resetCameraPosition() {
        cameraX = 0;
        cameraY = 0;
        cameraZ = 0;
    }

    public void moveCameraToRight() {
        cameraX--;
    }

    public void moveCameraToLeft() {
        cameraX++;
    }

    public void moveCameraToUp() {
        cameraY++;
    }

    public void moveCameraToDown() {
        cameraY--;
    }

    public void moveCameraToFoward() {
        cameraZ++;
    }

    public void moveCameraToBackward() {
        cameraZ--;
    }

    public void copyFrom(CameraManager source) {
        this.isVanillaCamera = source.isVanillaCamera;
        this.cameraZ = source.cameraZ;
        this.cameraX = source.cameraX;
        this.cameraY = source.cameraY;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("isCameraVanilla", isVanillaCamera);
        nbt.putInt("cameraX", cameraX);
        nbt.putInt("cameraY", cameraY);
        nbt.putInt("cameraZ", cameraZ);
    }

    public void loadNBTData(CompoundTag nbt) {
        isVanillaCamera = nbt.getBoolean("isCameraVanilla");
        cameraX = nbt.getInt("cameraX");
        cameraY = nbt.getInt("cameraY");
        cameraZ = nbt.getInt("cameraZ");
    }

}
