package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.sound.ModMusics;
import net.h8sh.playermod.world.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class DimensionEvent {

    @SubscribeEvent
    public static void onPlayerChangingDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getTo().equals(ModDimensions.WONDERLANDS_KEY)) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
            Minecraft.getInstance().getMusicManager().startPlaying(ModMusics.CUSTOM_MUSIC_MENU);
        }
    }

}
