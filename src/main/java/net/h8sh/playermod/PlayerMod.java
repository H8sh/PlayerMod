package net.h8sh.playermod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.h8sh.playermod.block.ModBlocks;
import net.h8sh.playermod.block.entity.ModBlockEntities;
import net.h8sh.playermod.block.entity.client.AdamBlockRenderer;
import net.h8sh.playermod.block.entity.client.PaladinLecternRenderer;
import net.h8sh.playermod.block.entity.client.profession.*;
import net.h8sh.playermod.block.entity.client.PnjBlockRenderer;
import net.h8sh.playermod.capability.profession.reader.ProfessionTypes;
import net.h8sh.playermod.config.WonderlandsModClientConfigs;
import net.h8sh.playermod.config.WonderlandsModCommonConfigs;
import net.h8sh.playermod.config.WonderlandsModServerConfigs;
import net.h8sh.playermod.effect.ModEffects;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.client.*;
import net.h8sh.playermod.fluid.ModFluidTypes;
import net.h8sh.playermod.fluid.ModFluids;
import net.h8sh.playermod.item.ModItems;
import net.h8sh.playermod.item.ModTabs;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.potion.ModPotions;
import net.h8sh.playermod.skill.reader.SkillTypes;
import net.h8sh.playermod.sound.ModSounds;
import net.h8sh.playermod.world.dimension.mansion.MansionManager;
import net.h8sh.playermod.world.dimension.mansion.reader.Prototypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
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

import java.util.Map;

@Mod(PlayerMod.MODID)
public class PlayerMod {

    public static final String MODID = "playermod";

    public static final Logger LOGGER = LogUtils.getLogger();

    public PlayerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(this::jsonStructureReader);
        forgeBus.addListener(this::jsonProfessionReader);
        forgeBus.addListener(this::jsonSkillReader);

        modEventBus.addListener(this::commonSetup);

        ModTabs.register(modEventBus);

        ModPotions.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModEffects.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModItems.register(modEventBus);

        ModTabs.register(modEventBus);

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

    private void jsonStructureReader(AddReloadListenerEvent event) {
        event.addListener(new SimpleJsonResourceReloadListener((new GsonBuilder()).create(), "library") {
            @Override
            protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
                pObject.forEach((resourceLocation, jsonStructureElement) -> {
                    try {
                        JsonObject jsonObject = GsonHelper.convertToJsonObject(jsonStructureElement, "prototypes");

                        Gson gson = new Gson();
                        Prototypes prototypes = gson.fromJson(jsonObject, Prototypes.class);
                        MansionManager.setPrototypesFromJson(prototypes);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        System.out.println("Json file failed to load for mansion generation");
                    }
                });
            }
        });
    }

    private void jsonProfessionReader(AddReloadListenerEvent event) {
        event.addListener(new SimpleJsonResourceReloadListener((new GsonBuilder()).create(), "profession") {
            @Override
            protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
                pObject.forEach((resourceLocation, jsonProfessionElement) -> {
                    try {
                        JsonObject jsonObject = GsonHelper.convertToJsonObject(jsonProfessionElement, "professions");

                        Gson gson = new Gson();
                        ProfessionTypes professionsType = gson.fromJson(jsonObject, ProfessionTypes.class);
                        ProfessionTypes.setPrototypesFromJson(professionsType);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        System.out.println("Json file failed to load for profession");
                    }
                });
            }
        });
    }

    private void jsonSkillReader(AddReloadListenerEvent event) {
        event.addListener(new SimpleJsonResourceReloadListener((new GsonBuilder()).create(), "skill") {
            @Override
            protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
                pObject.forEach((resourceLocation, jsonProfessionElement) -> {
                    try {
                        JsonObject jsonObject = GsonHelper.convertToJsonObject(jsonProfessionElement, "skills");

                        Gson gson = new Gson();
                        SkillTypes professionsType = gson.fromJson(jsonObject, SkillTypes.class);
                        SkillTypes.setPrototypesFromJson(professionsType);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        System.out.println("Json file failed to load for skill");
                    }
                });
            }
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

           /* ItemBlockRenderTypes.setRenderLayer(Fluids.WATER.getSource(), ModRenderTypes.brightSolid());
            ItemBlockRenderTypes.setRenderLayer(Fluids.WATER.getFlowing(), ModRenderTypes.brightSolid());*/

           /* ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_SOAP_WATER.get(), ModRenderTypes.brightSolid());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_SOAP_WATER.get(), ModRenderTypes.brightSolid());*/

            EntityRenderers.register(ModEntities.SWOUIFFI.get(), SwouiffiRenderer::new);
            EntityRenderers.register(ModEntities.LIVING_LAMPPOST.get(), LivingLamppostRenderer::new);
            EntityRenderers.register(ModEntities.CRYSTAL.get(), CrystalRenderer::new);
            EntityRenderers.register(ModEntities.CUSTOM_PNJ.get(), PnjEntityRenderer::new);
            EntityRenderers.register(ModEntities.CAMERA.get(), CameraRenderer::new);


            BlockEntityRenderers.register(ModBlockEntities.ANIMATED_BLOCK_ENTITY.get(), PaladinLecternRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.PNJ_BLOCK_ENTITY.get(), PnjBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.ADAM_BLOCK_ENTITY.get(), AdamBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.PLINTH_BLOCK_ENTITY.get(), PlinthBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.ROGUE_BLOCK_ENTITY.get(), RogueBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.PALADIN_BLOCK_ENTITY.get(), PaladinBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.WIZARD_BLOCK_ENTITY.get(), WizardBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.BERSERKER_BLOCK_ENTITY.get(), BerserkerBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.DRUID_BLOCK_ENTITY.get(), DruidBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.INVOCATOR_BLOCK_ENTITY.get(), InvocatorBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.GUNSLINGER_BLOCK_ENTITY.get(), GunslingerBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.MECHANIC_BLOCK_ENTITY.get(), MechanicBlockRenderer::new);
        }
    }
}
