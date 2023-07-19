package net.h8sh.playermod;

import com.mojang.logging.LogUtils;
import net.h8sh.playermod.block.ModBlocks;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.client.SwouiffiRenderer;
import net.h8sh.playermod.item.ModItems;
import net.h8sh.playermod.item.ModTabs;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.sound.ModSounds;
import net.h8sh.playermod.world.dimension.ModDimensions;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PlayerMod.MODID)
public class PlayerMod {
    public static final String MODID = "playermod";

    public static final Logger LOGGER = LogUtils.getLogger();


    public PlayerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModTabs.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModItems.register(modEventBus);

        ModTabs.register(modEventBus);

        ModDimensions.register();

        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {

        });
        ModMessages.register();

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.Swouiffi.get(), SwouiffiRenderer::new);
        }
    }
}
