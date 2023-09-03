package net.h8sh.playermod.potion;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, PlayerMod.MODID);

    // Reputation Upgrade Skill ----------------------------------------------------------------------------------------
    public static final RegistryObject<Potion> REPUTATION_POTION = POTIONS.register("reputation_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SMOKE.get(), 200, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }

}
