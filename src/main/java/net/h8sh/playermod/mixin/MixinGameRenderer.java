package net.h8sh.playermod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.h8sh.playermod.capability.camera.CameraManager;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Mutable
    @Final
    @Shadow
    final Minecraft minecraft;
    @Mutable
    @Final
    @Shadow
    private final LightTexture lightTexture;
    @Final
    @Shadow
    private final Camera mainCamera = new Camera();

    @Shadow
    private float renderDistance;

    @Shadow
    private int tick;

    @Shadow
    private boolean renderHand = true;

    protected MixinGameRenderer(LightTexture lightTexture, Minecraft minecraft) {
        this.lightTexture = lightTexture;
        this.minecraft = minecraft;
    }

    @Shadow
    public abstract void pick(float pPartialTicks);

    @Shadow
    protected abstract boolean shouldRenderBlockOutline();

    @Shadow
    protected abstract double getFov(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting);

    @Shadow
    public abstract Matrix4f getProjectionMatrix(double pFov);

    @Shadow
    protected abstract void bobHurt(PoseStack pPoseStack, float pPartialTicks);

    @Shadow
    protected abstract void bobView(PoseStack pPoseStack, float pPartialTicks);

    @Shadow
    public abstract void resetProjectionMatrix(Matrix4f pMatrix);

    @Shadow
    protected abstract void renderItemInHand(PoseStack pPoseStack, Camera pActiveRenderInfo, float pPartialTicks);


    @Inject(method = "renderLevel", at = @At("HEAD"), cancellable = true)
    private void renderLevel(float pPartialTicks, long pFinishTimeNano, PoseStack pPoseStack, CallbackInfo ci) {
        ci.cancel();

        GameRenderer gameRenderer = (GameRenderer) (Object) this;

        this.lightTexture.updateLightTexture(pPartialTicks);
        if (this.minecraft.getCameraEntity() == null) {
            this.minecraft.setCameraEntity(this.minecraft.player);
        }

        this.pick(pPartialTicks);
        this.minecraft.getProfiler().push("center");
        boolean flag = this.shouldRenderBlockOutline();
        this.minecraft.getProfiler().popPush("camera");
        Camera camera = this.mainCamera;
        this.renderDistance = (float) (this.minecraft.options.getEffectiveRenderDistance() * 16);
        PoseStack posestack = new PoseStack();
        double d0 = this.getFov(camera, pPartialTicks, true);
        posestack.mulPoseMatrix(this.getProjectionMatrix(d0));
        this.bobHurt(posestack, pPartialTicks);
        if (this.minecraft.options.bobView().get()) {
            this.bobView(posestack, pPartialTicks);
        }

        float f = this.minecraft.options.screenEffectScale().get().floatValue();
        float f1 = Mth.lerp(pPartialTicks, this.minecraft.player.oSpinningEffectIntensity, this.minecraft.player.spinningEffectIntensity) * f * f;
        if (f1 > 0.0F) {
            int i = this.minecraft.player.hasEffect(MobEffects.CONFUSION) ? 7 : 20;
            float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
            f2 *= f2;
            Axis axis = Axis.of(new Vector3f(0.0F, Mth.SQRT_OF_TWO / 2.0F, Mth.SQRT_OF_TWO / 2.0F));
            posestack.mulPose(axis.rotationDegrees(((float) this.tick + pPartialTicks) * (float) i));
            posestack.scale(1.0F / f2, 1.0F, 1.0F);
            float f3 = -((float) this.tick + pPartialTicks) * (float) i;
            posestack.mulPose(axis.rotationDegrees(f3));
        }

        Matrix4f matrix4f = posestack.last().pose();
        this.resetProjectionMatrix(matrix4f);



        camera.setup(this.minecraft.level, (Entity) (this.minecraft.getCameraEntity() == null ? this.minecraft.player : this.minecraft.getCameraEntity()), !this.minecraft.options.getCameraType().isFirstPerson(), this.minecraft.options.getCameraType().isMirrored(), pPartialTicks);

        //MODIFIED

        /*camera.setPosition(CameraManager.getCameraX() + this.minecraft.player.getX(),
                CameraManager.getCameraY() + this.minecraft.player.getY() + 2,
                CameraManager.getCameraZ() + this.minecraft.player.getZ() + 2);*/

        Entity cameraEntity = (Entity) (this.minecraft.getCameraEntity() == null ? this.minecraft.player : this.minecraft.getCameraEntity());

        camera.setPosition(Mth.lerp((double) pPartialTicks, cameraEntity.xo, CameraManager.getCameraX() + cameraEntity.getX()),Mth.lerp((double) pPartialTicks, cameraEntity.yo, CameraManager.getCameraY() + cameraEntity.getY()) + (double) Mth.lerp(pPartialTicks, camera.eyeHeightOld, camera.eyeHeight), Mth.lerp((double) pPartialTicks, cameraEntity.zo, CameraManager.getCameraZ() + cameraEntity.getZ()));

        camera.move(-camera.getMaxZoom(4.0D), 0.0D, 0.0D);

        net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles cameraSetup = net.minecraftforge.client.ForgeHooksClient.onCameraSetup(gameRenderer, camera, pPartialTicks);
        camera.setAnglesInternal(cameraSetup.getYaw(), cameraSetup.getPitch());
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(cameraSetup.getRoll()));

        pPoseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        Matrix3f matrix3f = (new Matrix3f(pPoseStack.last().normal())).invert();
        RenderSystem.setInverseViewRotationMatrix(matrix3f);
        this.minecraft.levelRenderer.prepareCullFrustum(pPoseStack, camera.getPosition(), this.getProjectionMatrix(Math.max(d0, (double) this.minecraft.options.fov().get().intValue())));
        this.minecraft.levelRenderer.renderLevel(pPoseStack, pPartialTicks, pFinishTimeNano, flag, camera, gameRenderer, this.lightTexture, matrix4f);
        this.minecraft.getProfiler().popPush("forge_render_last");
        net.minecraftforge.client.ForgeHooksClient.dispatchRenderStage(net.minecraftforge.client.event.RenderLevelStageEvent.Stage.AFTER_LEVEL, this.minecraft.levelRenderer, posestack, matrix4f, this.minecraft.levelRenderer.getTicks(), camera, this.minecraft.levelRenderer.getFrustum());
        this.minecraft.getProfiler().popPush("hand");
        if (this.renderHand) {
            RenderSystem.clear(256, Minecraft.ON_OSX);
            this.renderItemInHand(pPoseStack, camera, pPartialTicks);
        }

        this.minecraft.getProfiler().pop();

    }

}
