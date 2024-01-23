package net.h8sh.playermod.event;

import net.h8sh.playermod.screen.cinematic.EndPortalCinematic;
import net.h8sh.playermod.screen.pnj.PnjBlockScreen;
import net.h8sh.playermod.screen.profession.PaladinBookScreen;
import net.h8sh.playermod.sound.ModMusics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static net.h8sh.playermod.PlayerMod.MODID;
import static net.h8sh.playermod.screen.cinematic.EndPortalCinematic.createResourceLocations;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class ScreenEvent {
    private static final List<ResourceLocation> images = createResourceLocations();
    private static boolean isEndPortalCinematicScreenOpen = false;
    private static boolean shouldOpenEndPortalCinematicScreen = true;
    private static boolean shouldOpenPaladinBookScreen = false;
    private static boolean shouldOpenPnjBlockScreen = false;

    public static boolean openPnjBlockScreen() {
        return shouldOpenPnjBlockScreen = true;
    }

    public static void closePnjBlockScreen() {
        shouldOpenPnjBlockScreen = false;
    }

    public static void openPaladinBookScreen() {
        shouldOpenPaladinBookScreen = true;
    }

    public static void closePaladinBookScreen() {
        shouldOpenPaladinBookScreen = false;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Screen currentScreen = Minecraft.getInstance().screen;

            if (!isEndPortalCinematicScreenOpen && shouldOpenEndPortalCinematicScreen && currentScreen != null && currentScreen.getClass() == WinScreen.class) {
                Minecraft.getInstance().setScreen(new EndPortalCinematic(images, ModMusics.CUSTOM_MUSIC_MENU));
                isEndPortalCinematicScreenOpen = true;
            } else if (isEndPortalCinematicScreenOpen && (currentScreen == null || currentScreen.getClass() != EndPortalCinematic.class)) {
                isEndPortalCinematicScreenOpen = false;
                shouldOpenEndPortalCinematicScreen = false;
            }
            if (shouldOpenPaladinBookScreen) {
                Minecraft.getInstance().setScreen(new PaladinBookScreen());
                closePaladinBookScreen();
            }

            if (shouldOpenPnjBlockScreen) {
                Minecraft.getInstance().setScreen(new PnjBlockScreen());
                closePnjBlockScreen();
            }
        }
    }


}