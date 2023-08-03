package net.h8sh.playermod;

import com.mojang.logging.LogUtils;
import net.h8sh.playermod.block.ModBlocks;
import net.h8sh.playermod.block.entity.ModBlockEntities;
import net.h8sh.playermod.block.entity.client.PaladinLecternRenderer;
import net.h8sh.playermod.config.WonderlandsModClientConfigs;
import net.h8sh.playermod.config.WonderlandsModCommonConfigs;
import net.h8sh.playermod.config.WonderlandsModServerConfigs;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.client.CrystalRenderer;
import net.h8sh.playermod.entity.client.LivingLamppostRenderer;
import net.h8sh.playermod.entity.client.SwouiffiRenderer;
import net.h8sh.playermod.item.ModItems;
import net.h8sh.playermod.item.ModTabs;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.sound.ModSounds;
import net.h8sh.playermod.world.dimension.ModDimensions;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(PlayerMod.MODID)
public class PlayerMod {
    public static final String MODID = "playermod";

    public static final Logger LOGGER = LogUtils.getLogger();

    public PlayerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModTabs.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModItems.register(modEventBus);

        ModTabs.register(modEventBus);

        ModDimensions.register();

        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus);

        GeckoLib.initialize();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, WonderlandsModClientConfigs.SPEC, "wonderlandsmod-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WonderlandsModCommonConfigs.SPEC, "wonderlandsmod-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, WonderlandsModServerConfigs.SPEC, "wonderlandsmod-server.toml");

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
            EntityRenderers.register(ModEntities.SWOUIFFI.get(), SwouiffiRenderer::new);
            EntityRenderers.register(ModEntities.LIVING_LAMPPOST.get(), LivingLamppostRenderer::new);
            EntityRenderers.register(ModEntities.CRYSTAL.get(), CrystalRenderer::new);


            BlockEntityRenderers.register(ModBlockEntities.ANIMATED_BLOCK_ENTITY.get(), PaladinLecternRenderer::new);
        }
    }
}
