package net.h8sh.playermod.screen.cinematic;

import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.packet.travelling.OnChangedDimensionToWonderlandsHomeC2SPacket;
import net.h8sh.playermod.sound.ModMusics;
import net.h8sh.playermod.sound.ModSounds;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

import java.util.ArrayList;
import java.util.List;

import static net.h8sh.playermod.PlayerMod.MODID;

public class EndPortalCinematic extends Screen {

    private static final String TEXTURE_PATH = "textures/scene/scene";
    private static List<ResourceLocation> images;
    long delayMillis = 1000 / 24;
    private Music music;
    private int currentImageIndex;

    public EndPortalCinematic(List<ResourceLocation> images, Music music) {
        super(GameNarrator.NO_TITLE);
        EndPortalCinematic.images = images;
        this.currentImageIndex = 0;
        this.music = music;
    }

    public static List<ResourceLocation> createResourceLocations() {
        List<ResourceLocation> resourceLocations = new ArrayList<>();

        for (int i = 1; i <= 400; i++) {
            String sceneNumber = String.format("%05d", i);
            String texturePath = TEXTURE_PATH + sceneNumber + ".png";
            ResourceLocation resourceLocation = new ResourceLocation(MODID, texturePath);
            resourceLocations.add(resourceLocation);
        }

        return resourceLocations;
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void tick() {
        this.minecraft.getMusicManager().tick();
        this.minecraft.getSoundManager().tick(false);

    }

    @Override
    public void removed() {
        this.minecraft.getMusicManager().stopPlaying(music);
    }

    @Override
    public Music getBackgroundMusic() {
        return this.music;
    }


    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void render(GuiGraphics p_281274_, int p_283012_, int p_282072_, float p_282608_) {

        if (currentImageIndex < images.size()) {

            p_281274_.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            p_281274_.blit(images.get(currentImageIndex), 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
            p_281274_.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.BackgroundRendered(this, p_281274_));
            super.render(p_281274_, p_283012_, p_282072_, p_282608_);
            currentImageIndex++;

            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            this.onClose();
            removed();
        }
    }

    @Override
    public void onClose() {
        this.minecraft.popGuiLayer();
        //ModMessages.sendToServer(new OnChangedDimensionToWonderlandsHomeC2SPacket());
    }
}
