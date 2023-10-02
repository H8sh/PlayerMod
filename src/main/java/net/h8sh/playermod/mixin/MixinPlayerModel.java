package net.h8sh.playermod.mixin;

import net.h8sh.playermod.animation.CustomPlayerAnimation;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(PlayerModel.class)
public abstract class MixinPlayerModel<T extends LivingEntity> extends HumanoidModel<T> {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    private static ModelPart root;

    public MixinPlayerModel(ModelPart root) {
        super(root);
    }

    private static Optional<ModelPart> getAnyDescendantWithName(String pName) {
        return pName.equals("root") ? Optional.of(root()) : root().getAllParts().filter((p_233400_) -> {
            return p_233400_.hasChild(pName);
        }).findFirst().map((p_233397_) -> {
            return p_233397_.getChild(pName);
        });
    }

    private static float getElapsedSeconds(AnimationDefinition pAnimationDefinition, long pAccumulatedTime) {
        float f = (float) pAccumulatedTime / 1000.0F;
        return pAnimationDefinition.looping() ? f % pAnimationDefinition.lengthInSeconds() : f;
    }

    private static void animate(AnimationDefinition pAnimationDefinition, long pAccumulatedTime, float pScale, Vector3f pAnimationVecCache) {
        float f = getElapsedSeconds(pAnimationDefinition, pAccumulatedTime);

        for (Map.Entry<String, List<AnimationChannel>> entry : pAnimationDefinition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = getAnyDescendantWithName(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent((p_232330_) -> {
                list.forEach((p_288241_) -> {
                    Keyframe[] akeyframe = p_288241_.keyframes();
                    int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, (p_232315_) -> {
                        return f <= akeyframe[p_232315_].timestamp();
                    }) - 1);
                    int j = Math.min(akeyframe.length - 1, i + 1);
                    Keyframe keyframe = akeyframe[i];
                    Keyframe keyframe1 = akeyframe[j];
                    float f1 = f - keyframe.timestamp();
                    float f2;
                    if (j != i) {
                        f2 = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                    } else {
                        f2 = 0.0F;
                    }

                    keyframe1.interpolation().apply(pAnimationVecCache, f2, akeyframe, i, j, pScale);
                    p_288241_.target().apply(p_232330_, pAnimationVecCache);
                });
            });
        }

    }

    @Inject(method = "createMesh",
            at = @At("HEAD"), cancellable = true)
    private static void createMesh(CubeDeformation p_170826_, boolean p_170827_, CallbackInfoReturnable<MeshDefinition> cir) {
        cir.cancel();
        cir.setReturnValue(createCustomPlayer());
    }

    private static ModelPart root() {
        return root.getChild("body");
    }

    private static MeshDefinition createCustomPlayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partdefinition = meshDefinition.getRoot();

        //KEEP ONLY BODY
        createEmptyPart(partdefinition, "head");
        createEmptyPart(partdefinition, "ear");
        createEmptyPart(partdefinition, "hat");
        createEmptyPart(partdefinition, "cloak");
        createEmptyPart(partdefinition, "left_leg");
        createEmptyPart(partdefinition, "right_pants");
        createEmptyPart(partdefinition, "jacket");
        createEmptyPart(partdefinition, "left_sleeve");
        createEmptyPart(partdefinition, "right_sleeve");
        createEmptyPart(partdefinition, "left_pants");
        createEmptyPart(partdefinition, "right_arm");
        createEmptyPart(partdefinition, "left_arm");
        createEmptyPart(partdefinition, "right_leg");
        createEmptyPart(partdefinition, "left_leg");

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition steve_head = body.addOrReplaceChild("steve_head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_right_leg_full = chest.addOrReplaceChild("steve_right_leg_full", CubeListBuilder.create(), PartPose.offset(-2.0F, 6.0F, 0.0F));

        PartDefinition steve_right_leg_bottom = steve_right_leg_full.addOrReplaceChild("steve_right_leg_bottom", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition steve_right_leg = steve_right_leg_full.addOrReplaceChild("steve_right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_left_leg_full = chest.addOrReplaceChild("steve_left_leg_full", CubeListBuilder.create(), PartPose.offset(2.0F, 6.0F, 0.0F));

        PartDefinition steve_left_bottom = steve_left_leg_full.addOrReplaceChild("steve_left_bottom", CubeListBuilder.create().texOffs(24, 26).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition steve_left_leg = steve_left_leg_full.addOrReplaceChild("steve_left_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_right_arm_full = chest.addOrReplaceChild("steve_right_arm_full", CubeListBuilder.create(), PartPose.offset(-4.0F, -4.0F, 0.0F));

        PartDefinition steve_right_arm = steve_right_arm_full.addOrReplaceChild("steve_right_arm", CubeListBuilder.create().texOffs(32, 36).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_right_forearm = steve_right_arm_full.addOrReplaceChild("steve_right_forearm", CubeListBuilder.create().texOffs(40, 20).addBox(-2.0F, 1.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, 1.0F));

        PartDefinition steve_left_arm_full = chest.addOrReplaceChild("steve_left_arm_full", CubeListBuilder.create(), PartPose.offset(4.0F, -4.0F, 0.0F));

        PartDefinition steve_left_forearm = steve_left_arm_full.addOrReplaceChild("steve_left_forearm", CubeListBuilder.create().texOffs(16, 36).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.0F, 1.0F));

        PartDefinition steve_left_arm = steve_left_arm_full.addOrReplaceChild("steve_left_arm", CubeListBuilder.create().texOffs(36, 10).addBox(0.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));


        return meshDefinition;
    }

    private static PartDefinition createEmptyPart(PartDefinition partdefinition, String name) {
        return partdefinition.addOrReplaceChild(name, CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0, CubeDeformation.NONE), PartPose.ZERO);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void PlayerModel(ModelPart pRoot, boolean pSlim, CallbackInfo info) {
        this.root = pRoot;
    }

    protected void animateWalk(AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxAnimationSpeed, float pAnimationScaleFactor) {
        long i = (long) (pLimbSwing * 50.0F * pMaxAnimationSpeed);
        float f = Math.min(pLimbSwingAmount * pAnimationScaleFactor, 1.0F);
        animate(pAnimationDefinition, i, f, ANIMATION_VECTOR_CACHE);
    }

    protected void animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed) {
        pAnimationState.updateTime(pAgeInTicks, pSpeed);
        pAnimationState.ifStarted((p_233392_) -> {
            animate(pAnimationDefinition, p_233392_.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE);
        });
    }

    @Inject(method = "Lnet/minecraft/client/model/PlayerModel;setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
            at = @At("HEAD"), cancellable = true)
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {

        ci.cancel();

        root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.isAlive()) {
            animateWalk(CustomPlayerAnimation.WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        if (Minecraft.getInstance().options.keyJump.isDown()) {
            //animate(CustomPlayerAnimation.JUMP, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        if (Minecraft.getInstance().options.keyShift.isDown()) {
            //animate(CustomPlayerAnimation.SHIFT, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        if (Minecraft.getInstance().options.keyAttack.isDown()) {
            //animate(CustomPlayerAnimation.ATTACK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyLeft.isDown()) {
            //animate(CustomPlayerAnimation.DASH_LEFT, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyRight.isDown()) {
            //animate(CustomPlayerAnimation.DASH_RIGHT, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyUp.isDown()) {
            //animate(CustomPlayerAnimation.DASH_UP, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyDown.isDown()) {
            //animate(CustomPlayerAnimation.DASH_DOWN, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }

        //HEAD ROTATION: -----------------------------------------------------------------------------------------------

        var head = this.body.getChild("steve_head");
        boolean flag = entity.getFallFlyingTicks() > 4;
        boolean flag1 = entity.isVisuallySwimming();
        head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        if (flag) {
            head.xRot = (-(float) Math.PI / 4F);
        } else if (this.swimAmount > 0.0F) {
            if (flag1) {
                head.xRot = this.rotlerpRad(this.swimAmount, head.xRot, (-(float) Math.PI / 4F));
            } else {
                head.xRot = this.rotlerpRad(this.swimAmount, head.xRot, headPitch * ((float) Math.PI / 180F));
            }
        } else {
            head.xRot = headPitch * ((float) Math.PI / 180F);
        }

    }

}
