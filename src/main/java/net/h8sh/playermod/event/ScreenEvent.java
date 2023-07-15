package net.h8sh.playermod.event;

import net.h8sh.playermod.screen.CinematicScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static net.h8sh.playermod.PlayerMod.MODID;
import static net.h8sh.playermod.screen.CinematicScreen.createResourceLocations;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class ScreenEvent {
    private static final List<ResourceLocation> images = createResourceLocations();
    private static boolean isCinematicScreenOpen = false;
    private static boolean shouldOpenCinematicScreen = true;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Screen currentScreen = Minecraft.getInstance().screen;

            if (!isCinematicScreenOpen && shouldOpenCinematicScreen && currentScreen != null && currentScreen.getClass() == TitleScreen.class) {
                Minecraft.getInstance().setScreen(new CinematicScreen(Component.literal("Cinematic screen loaded with success !!"),
                        images, Musics.CREDITS));
                isCinematicScreenOpen = true;
            } else if (isCinematicScreenOpen && (currentScreen == null || currentScreen.getClass() != CinematicScreen.class)) {
                isCinematicScreenOpen = false;
                shouldOpenCinematicScreen = false;
            }
        }
    }

}