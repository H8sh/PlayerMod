package net.h8sh.playermod.sound;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PlayerMod.MODID);

    public static final RegistryObject<SoundEvent> CUSTOM_SOUND_MENU = registerSoundEvent("custom_sound_menu");


    public static final RegistryObject<SoundEvent> LIVING_LAMPPOST_IDLE = registerSoundEvent("living_lamppost_idle");
    public static final RegistryObject<SoundEvent> LIVING_LAMPPOST_MOVING = registerSoundEvent("living_lamppost_moving");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(PlayerMod.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }


}
