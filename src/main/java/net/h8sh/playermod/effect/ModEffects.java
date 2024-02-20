package net.h8sh.playermod.effect;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.effect.rogue.FrizzEffect;
import net.h8sh.playermod.effect.rogue.SmokeEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, PlayerMod.MODID);

    public static final RegistryObject<MobEffect> SMOKE = MOB_EFFECTS.register("smog",
            () -> new SmokeEffect(MobEffectCategory.HARMFUL, 3124687));

    public static final RegistryObject<MobEffect> FRIZZ = MOB_EFFECTS.register("frizz",
            () -> new FrizzEffect(MobEffectCategory.HARMFUL, 3124687));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
