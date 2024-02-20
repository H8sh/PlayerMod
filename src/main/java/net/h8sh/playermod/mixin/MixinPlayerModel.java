package net.h8sh.playermod.mixin;

import net.h8sh.playermod.animation.ModAnimationStates;
import net.h8sh.playermod.animation.animations.AnimationManager;
import net.h8sh.playermod.animation.animations.CustomPlayerAnimation;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.capability.profession.Profession;
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
    static int deathTick = 0;
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

        PartDefinition steve_body = body.addOrReplaceChild("steve_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_chest = steve_body.addOrReplaceChild("steve_chest", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_right_leg_full = steve_body.addOrReplaceChild("steve_right_leg_full", CubeListBuilder.create(), PartPose.offset(-2.0F, 6.0F, 0.0F));

        PartDefinition steve_right_leg_bottom = steve_right_leg_full.addOrReplaceChild("steve_right_leg_bottom", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition steve_right_leg = steve_right_leg_full.addOrReplaceChild("steve_right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_left_leg_full = steve_body.addOrReplaceChild("steve_left_leg_full", CubeListBuilder.create(), PartPose.offset(2.0F, 6.0F, 0.0F));

        PartDefinition steve_left_bottom = steve_left_leg_full.addOrReplaceChild("steve_left_bottom", CubeListBuilder.create().texOffs(24, 26).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition steve_left_leg = steve_left_leg_full.addOrReplaceChild("steve_left_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_right_arm_full = steve_body.addOrReplaceChild("steve_right_arm_full", CubeListBuilder.create(), PartPose.offset(-4.0F, -4.0F, 0.0F));

        PartDefinition steve_right_arm = steve_right_arm_full.addOrReplaceChild("steve_right_arm", CubeListBuilder.create().texOffs(32, 36).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition steve_right_forearm = steve_right_arm_full.addOrReplaceChild("steve_right_forearm", CubeListBuilder.create().texOffs(40, 20).addBox(-2.0F, 1.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, 1.0F));

        PartDefinition steve_left_arm_full = steve_body.addOrReplaceChild("steve_left_arm_full", CubeListBuilder.create(), PartPose.offset(4.0F, -4.0F, 0.0F));

        PartDefinition steve_left_forearm = steve_left_arm_full.addOrReplaceChild("steve_left_forearm", CubeListBuilder.create().texOffs(16, 36).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.0F, 1.0F));

        PartDefinition steve_left_arm = steve_left_arm_full.addOrReplaceChild("steve_left_arm", CubeListBuilder.create().texOffs(36, 10).addBox(0.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition steve_head = steve_body.addOrReplaceChild("steve_head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition wizard_body = body.addOrReplaceChild("wizard_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_head = wizard_body.addOrReplaceChild("wizard_head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition wizard_face = wizard_head.addOrReplaceChild("wizard_face", CubeListBuilder.create().texOffs(41, 48).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair1 = wizard_head.addOrReplaceChild("hair1", CubeListBuilder.create().texOffs(90, 78).addBox(-1.0F, -5.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair16 = wizard_head.addOrReplaceChild("hair16", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition hair13 = hair16.addOrReplaceChild("hair13", CubeListBuilder.create().texOffs(31, 90).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair15 = hair16.addOrReplaceChild("hair15", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair15_r1 = hair15.addOrReplaceChild("hair15_r1", CubeListBuilder.create().texOffs(53, 35).addBox(-0.842F, -0.0603F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3681F, 6.7588F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition hair14 = hair16.addOrReplaceChild("hair14", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair14_r1 = hair14.addOrReplaceChild("hair14_r1", CubeListBuilder.create().texOffs(58, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair17 = wizard_head.addOrReplaceChild("hair17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair10 = hair17.addOrReplaceChild("hair10", CubeListBuilder.create().texOffs(0, 60).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair11 = hair17.addOrReplaceChild("hair11", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair11_r1 = hair11.addOrReplaceChild("hair11_r1", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair12 = hair17.addOrReplaceChild("hair12", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair12_r1 = hair12.addOrReplaceChild("hair12_r1", CubeListBuilder.create().texOffs(14, 34).addBox(-0.842F, -0.0603F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3681F, 6.7588F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition hair20 = wizard_head.addOrReplaceChild("hair20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair19 = hair20.addOrReplaceChild("hair19", CubeListBuilder.create().texOffs(89, 85).addBox(-2.95F, 12.0F, 2.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair18 = hair20.addOrReplaceChild("hair18", CubeListBuilder.create().texOffs(80, 86).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair21 = wizard_head.addOrReplaceChild("hair21", CubeListBuilder.create(), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition hair22 = hair21.addOrReplaceChild("hair22", CubeListBuilder.create().texOffs(85, 48).addBox(-2.95F, 12.0F, 2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair23 = hair21.addOrReplaceChild("hair23", CubeListBuilder.create().texOffs(71, 86).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair24 = wizard_head.addOrReplaceChild("hair24", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair6 = hair24.addOrReplaceChild("hair6", CubeListBuilder.create().texOffs(75, 73).addBox(4.0F, -5.0F, -5.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair7 = hair24.addOrReplaceChild("hair7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair7_r1 = hair7.addOrReplaceChild("hair7_r1", CubeListBuilder.create().texOffs(82, 10).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 1.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair25 = wizard_head.addOrReplaceChild("hair25", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair8 = hair25.addOrReplaceChild("hair8", CubeListBuilder.create().texOffs(19, 75).addBox(4.0F, -5.0F, -5.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 0.0F, 0.0F));

        PartDefinition hair9 = hair25.addOrReplaceChild("hair9", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.237F, 1.6595F, -2.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition hair9_r1 = hair9.addOrReplaceChild("hair9_r1", CubeListBuilder.create().texOffs(82, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.513F, -0.4095F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair26 = wizard_head.addOrReplaceChild("hair26", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition hair3 = hair26.addOrReplaceChild("hair3", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -3.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair2 = hair26.addOrReplaceChild("hair2", CubeListBuilder.create().texOffs(54, 65).addBox(-1.0F, -5.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair27 = wizard_head.addOrReplaceChild("hair27", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair5 = hair27.addOrReplaceChild("hair5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition hair4 = hair27.addOrReplaceChild("hair4", CubeListBuilder.create().texOffs(58, 12).addBox(5.0F, -5.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition hair28 = wizard_head.addOrReplaceChild("hair28", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition hair29 = hair28.addOrReplaceChild("hair29", CubeListBuilder.create().texOffs(62, 84).addBox(-2.95F, 12.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair30 = hair28.addOrReplaceChild("hair30", CubeListBuilder.create().texOffs(0, 46).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair31 = hair28.addOrReplaceChild("hair31", CubeListBuilder.create().texOffs(13, 73).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 4.0F, 1.0F));

        PartDefinition hat = wizard_head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat5 = hat.addOrReplaceChild("hat5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -1.7693F, -1.3928F, 1.7662F));

        PartDefinition hat1 = hat5.addOrReplaceChild("hat1", CubeListBuilder.create(), PartPose.offset(-1.9415F, 7.0F, 5.665F));

        PartDefinition hat1_r1 = hat1.addOrReplaceChild("hat1_r1", CubeListBuilder.create().texOffs(43, 0).addBox(-4.0F, -0.5F, -10.5F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.5F, -5.5F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat2 = hat5.addOrReplaceChild("hat2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2629F, 0.5F, 0.5481F, 0.0F, 0.6981F, 0.0F));

        PartDefinition hat2_r1 = hat2.addOrReplaceChild("hat2_r1", CubeListBuilder.create().texOffs(39, 24).addBox(-4.0F, -0.5F, -10.5F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3214F, 0.0F, -0.383F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat3 = hat5.addOrReplaceChild("hat3", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2629F, 0.5F, 0.5481F, 0.0F, 1.3963F, 0.0F));

        PartDefinition hat3_r1 = hat3.addOrReplaceChild("hat3_r1", CubeListBuilder.create().texOffs(0, 22).addBox(-4.0F, -0.5F, -10.5F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3214F, 0.0F, -0.383F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat4 = hat5.addOrReplaceChild("hat4", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2629F, 0.5F, 0.5481F, 0.0F, 2.2602F, 0.0F));

        PartDefinition hat4_r1 = hat4.addOrReplaceChild("hat4_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -0.5F, -9.5F, 12.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3214F, 0.0F, -0.383F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat6 = hat.addOrReplaceChild("hat6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat6_r1 = hat6.addOrReplaceChild("hat6_r1", CubeListBuilder.create().texOffs(0, 46).addBox(-5.0F, -1.5F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, 0.0F, -0.3231F, 0.7013F, -0.2302F));

        PartDefinition hat7 = hat.addOrReplaceChild("hat7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat7_r1 = hat7.addOrReplaceChild("hat7_r1", CubeListBuilder.create().texOffs(0, 60).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0506F, -8.41F, 0.7277F, -0.5452F, 0.6385F, -0.3768F));

        PartDefinition hat8 = hat.addOrReplaceChild("hat8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.004F, -11.2506F, 1.8782F, -0.3927F, 0.0F, 0.0F));

        PartDefinition hat8_r1 = hat8.addOrReplaceChild("hat8_r1", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -1.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0546F, -0.1593F, 0.8495F, -0.5452F, 0.6385F, -0.3768F));

        PartDefinition hat9 = hat.addOrReplaceChild("hat9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.004F, -13.2506F, 5.8782F, -0.7418F, 0.0F, 0.0F));

        PartDefinition hat9_r1 = hat9.addOrReplaceChild("hat9_r1", CubeListBuilder.create().texOffs(0, 83).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0546F, -0.1593F, 0.8495F, -0.5452F, 0.6385F, -0.3768F));

        PartDefinition neck = wizard_head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(45, 12).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition wizard_right_arm_full = wizard_body.addOrReplaceChild("wizard_right_arm_full", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 0.7F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition wizard_right_arm = wizard_right_arm_full.addOrReplaceChild("wizard_right_arm", CubeListBuilder.create().texOffs(51, 84).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_forearm = wizard_right_arm_full.addOrReplaceChild("wizard_right_forearm", CubeListBuilder.create().texOffs(25, 60).addBox(-1.0F, 1.0F, -2.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, 1.0F));

        PartDefinition wizard_right_shoulder_armor = wizard_right_arm_full.addOrReplaceChild("wizard_right_shoulder_armor", CubeListBuilder.create().texOffs(0, 34).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_hand = wizard_right_arm_full.addOrReplaceChild("wizard_right_hand", CubeListBuilder.create().texOffs(65, 48).addBox(-3.0F, 8.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_jewel = wizard_right_arm_full.addOrReplaceChild("wizard_right_jewel", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, 4.0F, -2.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_left_leg_full = wizard_body.addOrReplaceChild("wizard_left_leg_full", CubeListBuilder.create(), PartPose.offset(3.0F, 6.0F, 0.0F));

        PartDefinition wizard_left_bottom = wizard_left_leg_full.addOrReplaceChild("wizard_left_bottom", CubeListBuilder.create().texOffs(39, 35).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition wizard_left_leg = wizard_left_leg_full.addOrReplaceChild("wizard_left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_left_leg_r1 = wizard_left_leg.addOrReplaceChild("wizard_left_leg_r1", CubeListBuilder.create().texOffs(78, 24).addBox(-2.3F, -2.8F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition wizard_right_leg_full = wizard_body.addOrReplaceChild("wizard_right_leg_full", CubeListBuilder.create(), PartPose.offset(-3.0F, 6.0F, 0.0F));

        PartDefinition wizard_right_leg_bottom = wizard_right_leg_full.addOrReplaceChild("wizard_right_leg_bottom", CubeListBuilder.create().texOffs(78, 35).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition wizard_right_leg = wizard_right_leg_full.addOrReplaceChild("wizard_right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_leg_r1 = wizard_right_leg.addOrReplaceChild("wizard_right_leg_r1", CubeListBuilder.create().texOffs(58, 73).addBox(-1.7F, -2.8F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition wizard_chest = wizard_body.addOrReplaceChild("wizard_chest", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition belt1 = wizard_chest.addOrReplaceChild("belt1", CubeListBuilder.create().texOffs(58, 65).addBox(-5.0F, 5.0F, -3.0F, 10.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock1 = wizard_chest.addOrReplaceChild("rock1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock1_r1 = rock1.addOrReplaceChild("rock1_r1", CubeListBuilder.create().texOffs(45, 0).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 6.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition rock2 = wizard_chest.addOrReplaceChild("rock2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock2_r1 = rock2.addOrReplaceChild("rock2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(5.25F, 1.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 6.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition little_chest1 = wizard_chest.addOrReplaceChild("little_chest1", CubeListBuilder.create().texOffs(33, 65).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock3 = wizard_chest.addOrReplaceChild("rock3", CubeListBuilder.create().texOffs(0, 73).addBox(2.85F, 9.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock4 = wizard_chest.addOrReplaceChild("rock4", CubeListBuilder.create().texOffs(72, 48).addBox(-5.95F, 9.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition belt2 = wizard_chest.addOrReplaceChild("belt2", CubeListBuilder.create().texOffs(71, 73).addBox(3.0F, 5.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition rock5 = wizard_chest.addOrReplaceChild("rock5", CubeListBuilder.create().texOffs(89, 58).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock6 = wizard_chest.addOrReplaceChild("rock6", CubeListBuilder.create().texOffs(22, 88).addBox(0.05F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition little_chest2 = wizard_chest.addOrReplaceChild("little_chest2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition little_chest2_r1 = little_chest2.addOrReplaceChild("little_chest2_r1", CubeListBuilder.create().texOffs(39, 24).addBox(-3.0F, -2.0F, -3.75F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition rock7 = wizard_chest.addOrReplaceChild("rock7", CubeListBuilder.create().texOffs(72, 58).addBox(-4.0F, 6.0F, -2.75F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_left_arm_full = wizard_body.addOrReplaceChild("wizard_left_arm_full", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 0.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition wizard_left_forearm = wizard_left_arm_full.addOrReplaceChild("wizard_left_forearm", CubeListBuilder.create().texOffs(89, 71).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.0F, 1.0F));

        PartDefinition wizard_left_arm = wizard_left_arm_full.addOrReplaceChild("wizard_left_arm", CubeListBuilder.create().texOffs(13, 88).addBox(1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition wizard_left_armor_shoulder = wizard_left_arm_full.addOrReplaceChild("wizard_left_armor_shoulder", CubeListBuilder.create().texOffs(34, 81).addBox(0.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_left_hand = wizard_left_arm_full.addOrReplaceChild("wizard_left_hand", CubeListBuilder.create().texOffs(38, 90).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 10.0F, 0.0F));

        PartDefinition wizard_left_jewel = wizard_left_arm_full.addOrReplaceChild("wizard_left_jewel", CubeListBuilder.create().texOffs(31, 48).addBox(0.0F, 4.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rogue_body = body.addOrReplaceChild("rogue_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rogue_head = rogue_body.addOrReplaceChild("rogue_head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition wizard_face2 = rogue_head.addOrReplaceChild("wizard_face2", CubeListBuilder.create().texOffs(41, 48).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair32 = rogue_head.addOrReplaceChild("hair32", CubeListBuilder.create().texOffs(90, 78).addBox(-1.0F, -5.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair33 = rogue_head.addOrReplaceChild("hair33", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition hair34 = hair33.addOrReplaceChild("hair34", CubeListBuilder.create().texOffs(31, 90).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair35 = hair33.addOrReplaceChild("hair35", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair15_r2 = hair35.addOrReplaceChild("hair15_r2", CubeListBuilder.create().texOffs(53, 35).addBox(-0.842F, -0.0603F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3681F, 6.7588F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition hair36 = hair33.addOrReplaceChild("hair36", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair14_r2 = hair36.addOrReplaceChild("hair14_r2", CubeListBuilder.create().texOffs(58, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair37 = rogue_head.addOrReplaceChild("hair37", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair38 = hair37.addOrReplaceChild("hair38", CubeListBuilder.create().texOffs(0, 60).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair39 = hair37.addOrReplaceChild("hair39", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair11_r2 = hair39.addOrReplaceChild("hair11_r2", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair40 = hair37.addOrReplaceChild("hair40", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair12_r2 = hair40.addOrReplaceChild("hair12_r2", CubeListBuilder.create().texOffs(14, 34).addBox(-0.842F, -0.0603F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3681F, 6.7588F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition hair41 = rogue_head.addOrReplaceChild("hair41", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair42 = hair41.addOrReplaceChild("hair42", CubeListBuilder.create().texOffs(89, 85).addBox(-2.95F, 12.0F, 2.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair43 = hair41.addOrReplaceChild("hair43", CubeListBuilder.create().texOffs(80, 86).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair44 = rogue_head.addOrReplaceChild("hair44", CubeListBuilder.create(), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition hair45 = hair44.addOrReplaceChild("hair45", CubeListBuilder.create().texOffs(85, 48).addBox(-2.95F, 12.0F, 2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair46 = hair44.addOrReplaceChild("hair46", CubeListBuilder.create().texOffs(71, 86).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair47 = rogue_head.addOrReplaceChild("hair47", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair48 = hair47.addOrReplaceChild("hair48", CubeListBuilder.create().texOffs(75, 73).addBox(4.0F, -5.0F, -5.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair49 = hair47.addOrReplaceChild("hair49", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair7_r2 = hair49.addOrReplaceChild("hair7_r2", CubeListBuilder.create().texOffs(82, 10).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 1.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair50 = rogue_head.addOrReplaceChild("hair50", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair51 = hair50.addOrReplaceChild("hair51", CubeListBuilder.create().texOffs(19, 75).addBox(4.0F, -5.0F, -5.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 0.0F, 0.0F));

        PartDefinition hair52 = hair50.addOrReplaceChild("hair52", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.237F, 1.6595F, -2.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition hair9_r2 = hair52.addOrReplaceChild("hair9_r2", CubeListBuilder.create().texOffs(82, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.513F, -0.4095F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition hair53 = rogue_head.addOrReplaceChild("hair53", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition hair54 = hair53.addOrReplaceChild("hair54", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -3.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair55 = hair53.addOrReplaceChild("hair55", CubeListBuilder.create().texOffs(54, 65).addBox(-1.0F, -5.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair56 = rogue_head.addOrReplaceChild("hair56", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair57 = hair56.addOrReplaceChild("hair57", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition hair58 = hair56.addOrReplaceChild("hair58", CubeListBuilder.create().texOffs(58, 12).addBox(5.0F, -5.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition hair59 = rogue_head.addOrReplaceChild("hair59", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition hair60 = hair59.addOrReplaceChild("hair60", CubeListBuilder.create().texOffs(62, 84).addBox(-2.95F, 12.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair61 = hair59.addOrReplaceChild("hair61", CubeListBuilder.create().texOffs(0, 46).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 1.0F));

        PartDefinition hair62 = hair59.addOrReplaceChild("hair62", CubeListBuilder.create().texOffs(13, 73).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 4.0F, 1.0F));

        PartDefinition hat10 = rogue_head.addOrReplaceChild("hat10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat11 = hat10.addOrReplaceChild("hat11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -1.7693F, -1.3928F, 1.7662F));

        PartDefinition hat12 = hat11.addOrReplaceChild("hat12", CubeListBuilder.create(), PartPose.offset(-1.9415F, 7.0F, 5.665F));

        PartDefinition hat1_r2 = hat12.addOrReplaceChild("hat1_r2", CubeListBuilder.create().texOffs(43, 0).addBox(-4.0F, -0.5F, -10.5F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.5F, -5.5F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat13 = hat11.addOrReplaceChild("hat13", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2629F, 0.5F, 0.5481F, 0.0F, 0.6981F, 0.0F));

        PartDefinition hat2_r2 = hat13.addOrReplaceChild("hat2_r2", CubeListBuilder.create().texOffs(39, 24).addBox(-4.0F, -0.5F, -10.5F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3214F, 0.0F, -0.383F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat14 = hat11.addOrReplaceChild("hat14", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2629F, 0.5F, 0.5481F, 0.0F, 1.3963F, 0.0F));

        PartDefinition hat3_r2 = hat14.addOrReplaceChild("hat3_r2", CubeListBuilder.create().texOffs(0, 22).addBox(-4.0F, -0.5F, -10.5F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3214F, 0.0F, -0.383F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat15 = hat11.addOrReplaceChild("hat15", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2629F, 0.5F, 0.5481F, 0.0F, 2.2602F, 0.0F));

        PartDefinition hat4_r2 = hat15.addOrReplaceChild("hat4_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -0.5F, -9.5F, 12.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3214F, 0.0F, -0.383F, 0.0F, -0.6981F, 0.0F));

        PartDefinition hat16 = hat10.addOrReplaceChild("hat16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat6_r2 = hat16.addOrReplaceChild("hat6_r2", CubeListBuilder.create().texOffs(0, 46).addBox(-5.0F, -1.5F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, 0.0F, -0.3231F, 0.7013F, -0.2302F));

        PartDefinition hat17 = hat10.addOrReplaceChild("hat17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat7_r2 = hat17.addOrReplaceChild("hat7_r2", CubeListBuilder.create().texOffs(0, 60).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0506F, -8.41F, 0.7277F, -0.5452F, 0.6385F, -0.3768F));

        PartDefinition hat18 = hat10.addOrReplaceChild("hat18", CubeListBuilder.create(), PartPose.offsetAndRotation(0.004F, -11.2506F, 1.8782F, -0.3927F, 0.0F, 0.0F));

        PartDefinition hat8_r2 = hat18.addOrReplaceChild("hat8_r2", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -1.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0546F, -0.1593F, 0.8495F, -0.5452F, 0.6385F, -0.3768F));

        PartDefinition hat19 = hat10.addOrReplaceChild("hat19", CubeListBuilder.create(), PartPose.offsetAndRotation(0.004F, -13.2506F, 5.8782F, -0.7418F, 0.0F, 0.0F));

        PartDefinition hat9_r2 = hat19.addOrReplaceChild("hat9_r2", CubeListBuilder.create().texOffs(0, 83).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0546F, -0.1593F, 0.8495F, -0.5452F, 0.6385F, -0.3768F));

        PartDefinition neck2 = rogue_head.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(45, 12).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition rogue_right_arm_full = rogue_body.addOrReplaceChild("rogue_right_arm_full", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 0.7F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition wizard_right_arm2 = rogue_right_arm_full.addOrReplaceChild("wizard_right_arm2", CubeListBuilder.create().texOffs(51, 84).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_forearm2 = rogue_right_arm_full.addOrReplaceChild("wizard_right_forearm2", CubeListBuilder.create().texOffs(25, 60).addBox(-1.0F, 1.0F, -2.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, 1.0F));

        PartDefinition wizard_right_shoulder_armor2 = rogue_right_arm_full.addOrReplaceChild("wizard_right_shoulder_armor2", CubeListBuilder.create().texOffs(0, 34).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_hand2 = rogue_right_arm_full.addOrReplaceChild("wizard_right_hand2", CubeListBuilder.create().texOffs(65, 48).addBox(-3.0F, 8.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_jewel2 = rogue_right_arm_full.addOrReplaceChild("wizard_right_jewel2", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, 4.0F, -2.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rogue_left_leg_full = rogue_body.addOrReplaceChild("rogue_left_leg_full", CubeListBuilder.create(), PartPose.offset(3.0F, 6.0F, 0.0F));

        PartDefinition wizard_left_bottom2 = rogue_left_leg_full.addOrReplaceChild("wizard_left_bottom2", CubeListBuilder.create().texOffs(39, 35).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition wizard_left_leg2 = rogue_left_leg_full.addOrReplaceChild("wizard_left_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_left_leg_r2 = wizard_left_leg2.addOrReplaceChild("wizard_left_leg_r2", CubeListBuilder.create().texOffs(78, 24).addBox(-2.3F, -2.8F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition rogue_right_leg_full = rogue_body.addOrReplaceChild("rogue_right_leg_full", CubeListBuilder.create(), PartPose.offset(-3.0F, 6.0F, 0.0F));

        PartDefinition wizard_right_leg_bottom2 = rogue_right_leg_full.addOrReplaceChild("wizard_right_leg_bottom2", CubeListBuilder.create().texOffs(78, 35).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition wizard_right_leg2 = rogue_right_leg_full.addOrReplaceChild("wizard_right_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_right_leg_r2 = wizard_right_leg2.addOrReplaceChild("wizard_right_leg_r2", CubeListBuilder.create().texOffs(58, 73).addBox(-1.7F, -2.8F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition rogue_chest = rogue_body.addOrReplaceChild("rogue_chest", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition belt3 = rogue_chest.addOrReplaceChild("belt3", CubeListBuilder.create().texOffs(58, 65).addBox(-5.0F, 5.0F, -3.0F, 10.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock8 = rogue_chest.addOrReplaceChild("rock8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock1_r2 = rock8.addOrReplaceChild("rock1_r2", CubeListBuilder.create().texOffs(45, 0).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 6.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition rock9 = rogue_chest.addOrReplaceChild("rock9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock2_r2 = rock9.addOrReplaceChild("rock2_r2", CubeListBuilder.create().texOffs(0, 0).addBox(5.25F, 1.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 6.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition little_chest3 = rogue_chest.addOrReplaceChild("little_chest3", CubeListBuilder.create().texOffs(33, 65).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock10 = rogue_chest.addOrReplaceChild("rock10", CubeListBuilder.create().texOffs(0, 73).addBox(2.85F, 9.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock11 = rogue_chest.addOrReplaceChild("rock11", CubeListBuilder.create().texOffs(72, 48).addBox(-5.95F, 9.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition belt4 = rogue_chest.addOrReplaceChild("belt4", CubeListBuilder.create().texOffs(71, 73).addBox(3.0F, 5.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition rock12 = rogue_chest.addOrReplaceChild("rock12", CubeListBuilder.create().texOffs(89, 58).addBox(-2.95F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rock13 = rogue_chest.addOrReplaceChild("rock13", CubeListBuilder.create().texOffs(22, 88).addBox(0.05F, 5.0F, 2.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition little_chest4 = rogue_chest.addOrReplaceChild("little_chest4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition little_chest2_r2 = little_chest4.addOrReplaceChild("little_chest2_r2", CubeListBuilder.create().texOffs(39, 24).addBox(-3.0F, -2.0F, -3.75F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition rock14 = rogue_chest.addOrReplaceChild("rock14", CubeListBuilder.create().texOffs(72, 58).addBox(-4.0F, 6.0F, -2.75F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rogue_left_arm_full = rogue_body.addOrReplaceChild("rogue_left_arm_full", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 0.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition wizard_left_forearm2 = rogue_left_arm_full.addOrReplaceChild("wizard_left_forearm2", CubeListBuilder.create().texOffs(89, 71).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.0F, 1.0F));

        PartDefinition wizard_left_arm2 = rogue_left_arm_full.addOrReplaceChild("wizard_left_arm2", CubeListBuilder.create().texOffs(13, 88).addBox(1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition wizard_left_armor_shoulder2 = rogue_left_arm_full.addOrReplaceChild("wizard_left_armor_shoulder2", CubeListBuilder.create().texOffs(34, 81).addBox(0.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_left_hand2 = rogue_left_arm_full.addOrReplaceChild("wizard_left_hand2", CubeListBuilder.create().texOffs(38, 90).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 10.0F, 0.0F));

        PartDefinition wizard_left_jewel2 = rogue_left_arm_full.addOrReplaceChild("wizard_left_jewel2", CubeListBuilder.create().texOffs(31, 48).addBox(0.0F, 4.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return meshDefinition;
    }

    private static PartDefinition createEmptyPart(PartDefinition partdefinition, String name) {
        return partdefinition.addOrReplaceChild(name, CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0, CubeDeformation.NONE), PartPose.ZERO);
    }

    protected static void animateWalk(AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxAnimationSpeed, float pAnimationScaleFactor) {
        long i = (long) (pLimbSwing * 50.0F * pMaxAnimationSpeed);
        float f = Math.min(pLimbSwingAmount * pAnimationScaleFactor, 1.0F);
        animate(pAnimationDefinition, i, f, ANIMATION_VECTOR_CACHE);
    }

    protected static void animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed) {
        pAnimationState.updateTime(pAgeInTicks, pSpeed);
        pAnimationState.ifStarted((p_233392_) -> {
            animate(pAnimationDefinition, p_233392_.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE);
        });
    }

    private static void setupAnimSteve(LivingEntity entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.STEVE_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.STEVE_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.STEVE_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.STEVE_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.STEVE_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void PlayerModel(ModelPart pRoot, boolean pSlim, CallbackInfo info) {
        this.root = pRoot;
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
            at = @At("HEAD"), cancellable = true)
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {

        ci.cancel();

        if (Profession.getProfession() == Profession.Professions.BASIC)
            setupAnimSteve(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.WIZARD)
            setupAnimWizard(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.PALADIN)
            setupAnimPaladin(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.DRUID)
            setupAnimDruid(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.FIREMETA)
            setupAnimFireMeta(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.WINDMETA)
            setupAnimWindMeta(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.AQUAMETA)
            setupAnimAquaMeta(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.SPIRITUSMETA)
            setupAnimSpiritusMeta(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.ROGUE)
            setupAnimRogue(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.INVOCATOR)
            setupAnimInvocator(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.GUNSLINGER)
            setupAnimGunslinger(entity, ageInTicks, limbSwing, limbSwingAmount);
        if (Profession.getProfession() == Profession.Professions.MECHANIC)
            setupAnimMechanic(entity, ageInTicks, limbSwing, limbSwingAmount);

        //HEAD ROTATION: -----------------------------------------------------------------------------------------------

        if (entity.isAlive() && !AnimationHandler.getPlayerFrontDash()) {
            var head = this.body.getChild(Profession.getProfessionName() + "_body").getChild(Profession.getProfessionName() + "_head");
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

    private void setupAnimMechanic(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.MECHANIC_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.MECHANIC_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.MECHANIC_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.MECHANIC_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.MECHANIC_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimGunslinger(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.GUNSLINGER_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.GUNSLINGER_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.GUNSLINGER_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.GUNSLINGER_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.GUNSLINGER_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimInvocator(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.INVOCATOR_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.INVOCATOR_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.INVOCATOR_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.INVOCATOR_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.INVOCATOR_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimRogue(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.ROGUE_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.ROGUE_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.ROGUE_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.ROGUE_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.ROGUE_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimSpiritusMeta(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.SPIRITUSMETA_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.SPIRITUSMETA_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.SPIRITUSMETA_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_DEATH, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.SPIRITUSMETA_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.SPIRITUSMETA_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimWindMeta(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.WINDMETA_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.WINDMETA_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.WINDMETA_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.WINDMETA_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.WINDMETA_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimAquaMeta(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.AQUAMETA_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.AQUAMETA_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.AQUAMETA_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.AQUAMETA_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.AQUAMETA_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimFireMeta(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.FIREMETA_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.FIREMETA_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.FIREMETA_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.FIREMETA_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.FIREMETA_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimDruid(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.DRUID_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.DRUID_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.DRUID_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.DRUID_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.DRUID_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimPaladin(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.PALADIN_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.PALADIN_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.PALADIN_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.PALADIN_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.PALADIN_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

    private void setupAnimWizard(T entity, float ageInTicks, float limbSwing, float limbSwingAmount) {
        if (entity.isDeadOrDying()) deathTick++;
        if (entity.isAlive()) deathTick = 0;

        root().getAllParts().forEach(ModelPart::resetPose);

        animate(ModAnimationStates.WIZARD_IDLE, CustomPlayerAnimation.STEVE_IDLE, ageInTicks, AnimationManager.WIZARD_IDLE_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_DEATH, CustomPlayerAnimation.STEVE_DEATH, deathTick, AnimationManager.WIZARD_DEATH_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_ATTACK, CustomPlayerAnimation.STEVE_ATTACK, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_ATTACK_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_IDLE_SHIFT_DOWN, CustomPlayerAnimation.STEVE_IDLE_SHIFT_DOWN, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_IDLE_SHIFT_DOWN_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_JUMP, CustomPlayerAnimation.STEVE_JUMP, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_JUMP_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_BACK_DASH, CustomPlayerAnimation.STEVE_DASH_BACK, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_BACK_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_FALL, CustomPlayerAnimation.STEVE_FALL, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_FALL_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_FRONT_DASH, CustomPlayerAnimation.STEVE_DASH_FRONT, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_FRONT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_LEFT_DASH, CustomPlayerAnimation.STEVE_DASH_LEFT, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_LEFT_DASH_ANIMATION_SPEED);

        animate(ModAnimationStates.WIZARD_RIGHT_DASH, CustomPlayerAnimation.STEVE_DASH_RIGHT, AnimationHandler.getCountTickAnimation(), AnimationManager.WIZARD_RIGHT_DASH_SPEED);

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_SHIFT_DOWN, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        if (entity.isAlive() && entity.walkAnimation.isMoving() && !AnimationHandler.getPlayerAttack() && !AnimationHandler.getPlayerShiftDown() && !AnimationHandler.getPlayerIdleShiftDown() && !AnimationHandler.getPlayerJump() && !AnimationHandler.getPlayerBackDash() && !AnimationHandler.getPlayerRightDash() && !AnimationHandler.getPlayerFrontDash() && !AnimationHandler.getPlayerLeftDash()) {
            animateWalk(CustomPlayerAnimation.STEVE_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
    }

}
