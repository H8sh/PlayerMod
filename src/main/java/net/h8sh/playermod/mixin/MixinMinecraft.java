package net.h8sh.playermod.mixin;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    
    @Shadow
    public abstract void updateTitle();

    @Final
    @Shadow
    private SoundManager soundManager;

    @Shadow
    public boolean noRender;
    
    @Final
    @Shadow
    public MouseHandler mouseHandler;

    @Shadow
    @Nullable
    public ClientLevel level;
    @Final
    @Shadow
    private Window window;

    @Shadow 
    private static final Logger LOGGER = LogUtils.getLogger();
    @Shadow
    private Thread gameThread;
    @Shadow
    @Nullable
    public Screen screen;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void setScreen(Screen pGuiScreen, CallbackInfo ci) {
        ci.cancel();
        
        Minecraft minecraft = (Minecraft) (Object) this;

        if (SharedConstants.IS_RUNNING_IN_IDE && Thread.currentThread() != this.gameThread) {
            LOGGER.error("setScreen called from non-game thread");
        }

        if (pGuiScreen == null && this.level == null) {
            pGuiScreen = new TitleScreen();
        }

        net.minecraftforge.client.ForgeHooksClient.clearGuiLayers(minecraft);
        Screen old = this.screen;
        if (pGuiScreen != null) {
            var event = new net.minecraftforge.client.event.ScreenEvent.Opening(old, pGuiScreen);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
            pGuiScreen = event.getNewScreen();
        }

        if (old != null && pGuiScreen != old) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.Closing(old));
            old.removed();
        }

        this.screen = pGuiScreen;
        if (this.screen != null) {
            this.screen.added();
        }

        BufferUploader.reset();
        if (pGuiScreen != null) {
            this.mouseHandler.releaseMouse();
            KeyMapping.releaseAll();
            pGuiScreen.init(minecraft, this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
            this.noRender = false;
        } else {
            this.soundManager.resume();
            this.mouseHandler.grabMouse();
        }

        this.updateTitle();
    }


}
