package net.h8sh.playermod.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.h8sh.playermod.PlayerMod;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

public class ModRenderTypes {

    public static RenderType brightSolid() {
        return CustomRenderTypes.BRIGHT_SOLID;
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = PlayerMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents {
        /*@SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("rendertype_water_mask"), DefaultVertexFormat.BLOCK), shaderInstance -> {
                CustomRenderTypes.brightSolidShader = shaderInstance;
            });
        }*/
    }

    public static class CustomRenderTypes extends RenderType {

        private static ShaderInstance brightSolidShader;
        private static final ShaderStateShard RENDERTYPE_BRIGHT_SOLID_SHADER = new ShaderStateShard(() -> brightSolidShader);
        public static RenderType BRIGHT_SOLID = brightSolid();

        private CustomRenderTypes(String s, VertexFormat v, VertexFormat.Mode m, int i, boolean b, boolean b2, Runnable r, Runnable r2) {
            super(s, v, m, i, b, b2, r, r2);
            throw new IllegalStateException("This class is not meant to be constructed!");
        }

        private static RenderType.CompositeState translucentState(RenderStateShard.ShaderStateShard pState) {
            return RenderType.CompositeState.builder().setLightmapState(LIGHTMAP).setShaderState(pState).setTextureState(BLOCK_SHEET_MIPPED).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setOutputState(TRANSLUCENT_TARGET).createCompositeState(true);
        }

        private static RenderType brightSolid() {
            return create("playermod_water_mask", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, true, translucentState(RENDERTYPE_BRIGHT_SOLID_SHADER));
        }
    }
}