package net.h8sh.playermod.screen.camera;

import net.h8sh.playermod.capability.camera.CameraManager;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.camera.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CameraScreen extends Screen {
    private static Component CAMERA_TITLE = Component.translatable("camera_screen");
    private CycleButton<CameraMod> cameraSwitch;

    public CameraScreen() {
        super(CAMERA_TITLE);
    }

    public static CycleButton<CameraMod> createCameraModButton(int pX, int pY, String pTranslationKey) {
        return CycleButton.builder(CameraMod::getDisplayName).withValues(CameraMod.values()).withInitialValue(CameraMod.Custom).create(pX, pY, 140, 20, Component.translatable(pTranslationKey), (cycleButton, cameraMod) -> {
            ModMessages.sendToServer(new ResetCameraToVanillaC2SPacket());
        });
    }

    @Override
    protected void init() {

        this.cameraSwitch = createCameraModButton(this.width - 160, 12, "Camera Type: ");
        this.cameraSwitch.active = true;
        this.cameraSwitch.visible = true;
        addRenderableWidget(this.cameraSwitch);
        addRenderableWidget(Button.builder(Component.literal("Reset Camera Position").withStyle(ChatFormatting.RED), (button) -> {
            if (!CameraManager.isVanillaCamera()) ModMessages.sendToServer(new ResetCameraC2SPacket());
        }).bounds(this.width - 400, 12, 120, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Left").withStyle(ChatFormatting.DARK_GRAY), (button) -> {
            if (!CameraManager.isVanillaCamera()) ModMessages.sendToServer(new MoveCameraToLeftC2SPacket());
        }).bounds(this.width - 400, 180, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Right").withStyle(ChatFormatting.DARK_GRAY), (button) -> {
            if (!CameraManager.isVanillaCamera()) ModMessages.sendToServer(new MoveCameraToRightC2SPacket());
        }).bounds(this.width - 100, 180, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Up").withStyle(ChatFormatting.DARK_GRAY), (button) -> {
            if (!CameraManager.isVanillaCamera()) ModMessages.sendToServer(new MoveCameraToUpC2SPacket());
        }).bounds(this.width - 200, 180, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Down").withStyle(ChatFormatting.DARK_GRAY), (button) -> {
            if (!CameraManager.isVanillaCamera()) ModMessages.sendToServer(new MoveCameraToDownC2SPacket());
        }).bounds(this.width - 300, 180, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Forward").withStyle(ChatFormatting.DARK_GRAY), (button) -> {
            if (!CameraManager.isVanillaCamera()) ModMessages.sendToServer(new MoveCameraToFowardC2SPacket());
        }).bounds(this.width - 400, 140, 80, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Backward").withStyle(ChatFormatting.DARK_GRAY), (button) -> {
            if (!CameraManager.isVanillaCamera()) ModMessages.sendToServer(new MoveCameraToBackwardC2SPacket());
        }).bounds(this.width - 100, 140, 80, 20).build());
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
