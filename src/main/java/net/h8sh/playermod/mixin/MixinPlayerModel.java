package net.h8sh.playermod.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerModel.class)
public abstract class MixinPlayerModel<T extends LivingEntity> extends HumanoidModel<T> {
    public MixinPlayerModel(ModelPart root) {
        super(root);
    }

    @Inject(method = "createMesh",
            at = @At("HEAD"), cancellable = true)
    private static void createMesh(CubeDeformation p_170826_, boolean p_170827_, CallbackInfoReturnable<MeshDefinition> cir) {
        cir.cancel();
        cir.setReturnValue(createCustomPlayer());
    }

    private static MeshDefinition createCustomPlayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partdefinition = meshDefinition.getRoot();

        //KEEP ONLY HEAD AND BODY
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

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition druid_tail = body.addOrReplaceChild("druid_tail", CubeListBuilder.create().texOffs(32, 24).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 2.0F));

        PartDefinition druid_right_arm = body.addOrReplaceChild("druid_right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -4.0F, 0.0F));

        PartDefinition druid_left_arm = body.addOrReplaceChild("druid_left_arm", CubeListBuilder.create().texOffs(16, 32).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -4.0F, 0.0F));

        PartDefinition druid_right_leg = body.addOrReplaceChild("druid_right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 6.0F, 0.0F));

        PartDefinition druid_left_leg = body.addOrReplaceChild("druid_left_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 6.0F, 0.0F));

        PartDefinition wizard_right_arm = body.addOrReplaceChild("wizard_right_arm", CubeListBuilder.create().texOffs(36, 37).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -4.0F, 0.0F));

        PartDefinition wizard_left_arm = body.addOrReplaceChild("wizard_left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -4.0F, 0.0F));

        PartDefinition wizard_right_leg = body.addOrReplaceChild("wizard_right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 6.0F, 0.0F));

        PartDefinition wizard_left_leg = body.addOrReplaceChild("wizard_left_leg", CubeListBuilder.create().texOffs(24, 25).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 6.0F, 0.0F));

        PartDefinition paladin_left_leg = body.addOrReplaceChild("paladin_left_leg", CubeListBuilder.create(), PartPose.offset(-0.9F, 19.5F, 0.0F));

        PartDefinition top_front_shoe = paladin_left_leg.addOrReplaceChild("top_front_shoe", CubeListBuilder.create(), PartPose.offset(-0.975F, -3.15F, 2.65F));

        PartDefinition cube_r1 = top_front_shoe.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.275F, -0.0706F, -0.0428F, 0.5F, 0.4F, 1.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r2 = top_front_shoe.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.275F, -0.25F, 0.0F, 0.5F, 0.4F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.8F, -0.9F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r3 = top_front_shoe.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.775F, -0.45F, -0.3F, 1.5F, 0.9F, 0.6F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.8F, -1.2F, -0.7854F, 0.0F, 0.0F));

        PartDefinition front_shoe = paladin_left_leg.addOrReplaceChild("front_shoe", CubeListBuilder.create(), PartPose.offset(-1.7F, -3.1F, 2.5F));

        PartDefinition cube_r4 = front_shoe.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.0551F, 0.0167F, -1.06F, 0.75F, 0.2F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.8513F, -0.1769F, -0.2395F));

        PartDefinition cube_r5 = front_shoe.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-0.05F, 0.0F, -1.1F, 0.75F, 0.2F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.7F, 0.9F, -0.6244F, -0.1769F, -0.2395F));

        PartDefinition cube_r6 = front_shoe.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-0.7672F, -0.06F, -1.1201F, 0.75F, 0.1F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.1F, 0.0F, -0.8712F, 0.1667F, 0.2251F));

        PartDefinition cube_r7 = front_shoe.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, 0.0F, -1.1F, 0.75F, 0.1F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.7F, 0.9F, -0.6269F, 0.1667F, 0.2251F));

        PartDefinition cube_r8 = front_shoe.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(2, 0).addBox(-0.5F, -1.4F, -0.6F, 1.1F, 0.9F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, 2.1F, 0.9F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r9 = front_shoe.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(6, 0).addBox(-1.8F, -0.273F, -2.764F, 1.55F, 0.35F, 2.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7F, 1.5F, -0.2F, -0.4102F, 0.0F, 0.0F));

        PartDefinition ankle = paladin_left_leg.addOrReplaceChild("ankle", CubeListBuilder.create().texOffs(8, 0).addBox(-2.85F, -5.2F, -1.15F, 1.5F, 2.3F, 2.4F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r10 = ankle.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(4, 12).addBox(-0.5F, -2.0F, -1.05F, 0.55F, 2.1F, 1.85F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, -3.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r11 = ankle.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(8, 8).addBox(0.15F, -2.0F, -1.3F, 0.5F, 2.1F, 2.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition right_front_plate = paladin_left_leg.addOrReplaceChild("right_front_plate", CubeListBuilder.create(), PartPose.offsetAndRotation(0.825F, -1.4274F, 0.4934F, 0.0083F, -0.1481F, -0.0529F));

        PartDefinition cube_r12 = right_front_plate.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-3.1877F, -3.0464F, -0.8055F, 0.25F, 0.85F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4417F, -2.3201F, -1.5002F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r13 = right_front_plate.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-1.1127F, -0.3881F, -0.9045F, 0.25F, 1.35F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -0.5818F, 2.3316F, -2.2078F, 0.0F, 0.0F));

        PartDefinition cube_r14 = right_front_plate.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-1.1127F, -1.5214F, -0.4555F, 0.25F, 1.8F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -0.6625F, 1.6487F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r15 = right_front_plate.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(6, 12).addBox(-1.1127F, -1.1041F, -0.6969F, 0.25F, 2.1F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -1.5008F, 1.5662F, -2.4522F, 0.0F, 0.0F));

        PartDefinition cube_r16 = right_front_plate.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0).addBox(-1.1127F, -0.8316F, -0.2535F, 0.25F, 1.35F, 0.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -2.1343F, 0.2059F, -1.2566F, 0.0F, 0.0F));

        PartDefinition cube_r17 = right_front_plate.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(14, 3).addBox(-3.1877F, -1.0411F, 0.0989F, 0.25F, 1.15F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4417F, -2.8694F, 0.973F, -2.5395F, 0.0F, 0.0F));

        PartDefinition cube_r18 = right_front_plate.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(10, 10).addBox(-1.1127F, -0.813F, -0.6759F, 0.25F, 1.35F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -1.4411F, 0.7274F, -0.7418F, 0.0F, 0.0F));

        PartDefinition left_front_plate = paladin_left_leg.addOrReplaceChild("left_front_plate", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.775F, -1.4274F, -0.8066F, 0.0083F, 0.1743F, 0.0532F));

        PartDefinition cube_r19 = left_front_plate.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0652F, -2.7278F, -0.6938F, 0.25F, 0.85F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2567F, -2.3043F, -0.2264F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r20 = left_front_plate.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9902F, -0.1912F, -0.6302F, 0.25F, 1.35F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -0.566F, 3.6054F, -2.2078F, 0.0F, 0.0F));

        PartDefinition cube_r21 = left_front_plate.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9902F, -1.2028F, -0.3438F, 0.25F, 1.8F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -0.6467F, 2.9225F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r22 = left_front_plate.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(12, 8).addBox(-0.9902F, -0.9794F, -0.3831F, 0.25F, 2.1F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -1.485F, 2.84F, -2.4522F, 0.0F, 0.0F));

        PartDefinition cube_r23 = left_front_plate.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9902F, -0.4939F, -0.2545F, 0.25F, 1.35F, 0.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -2.1185F, 1.4797F, -1.2566F, 0.0F, 0.0F));

        PartDefinition cube_r24 = left_front_plate.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(4, 14).addBox(-3.0652F, -0.9443F, 0.4224F, 0.25F, 1.15F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2567F, -2.8536F, 2.2469F, -2.5395F, 0.0F, 0.0F));

        PartDefinition cube_r25 = left_front_plate.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 11).addBox(-0.9902F, -0.5196F, -0.8431F, 0.25F, 1.35F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -1.4253F, 2.0013F, -0.7418F, 0.0F, 0.0F));

        PartDefinition back_top_plate = paladin_left_leg.addOrReplaceChild("back_top_plate", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.7253F, -4.0415F, -1.7218F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r26 = back_top_plate.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, 0).addBox(0.0952F, 1.0658F, -0.2398F, 0.55F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1838F, 0.3791F, 0.475F, 0.2375F, -1.0086F, 1.603F));

        PartDefinition cube_r27 = back_top_plate.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 0).addBox(-0.0921F, 0.8273F, -1.5729F, 0.95F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.264F, -1.0259F, 0.4133F, 0.3795F, -1.2254F, 1.446F));

        PartDefinition cube_r28 = back_top_plate.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(0, 0).addBox(-1.8159F, -0.1377F, -1.5729F, 0.8F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -1.0F, 0.5F, 1.2497F, 0.0844F, 0.2481F));

        PartDefinition cube_r29 = back_top_plate.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 0).addBox(-0.0764F, 0.6213F, 0.2706F, 0.55F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7238F, 0.4443F, 0.6251F, -0.3666F, -0.9083F, 1.6168F));

        PartDefinition cube_r30 = back_top_plate.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(2, 7).addBox(0.1228F, 0.5102F, -0.9834F, 1.05F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0947F, -0.8933F, 0.2594F, -0.6175F, -1.1801F, 1.9038F));

        PartDefinition cube_r31 = back_top_plate.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 0).addBox(-1.7037F, -0.1377F, -1.0134F, 0.8F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9766F, -1.1936F, 0.5667F, 1.2535F, -0.0981F, -0.2897F));

        PartDefinition cube_r32 = back_top_plate.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(11, 0).addBox(-2.05F, -0.75F, -0.15F, 1.35F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1253F, 0.0915F, 0.7718F, 1.2392F, 0.0F, 0.0F));

        PartDefinition heel = paladin_left_leg.addOrReplaceChild("heel", CubeListBuilder.create().texOffs(11, 11).addBox(-2.6922F, -0.2059F, -0.8798F, 1.0F, 0.25F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1922F, -1.5441F, -0.3702F));

        PartDefinition cube_r33 = heel.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 0).addBox(-0.575F, -0.125F, 0.1F, 0.55F, 0.25F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.0F, 0.0F, 1.165F, 0.3207F, 0.1767F));

        PartDefinition cube_r34 = heel.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 0).addBox(0.025F, -0.125F, 0.1F, 0.55F, 0.25F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6282F, 0.0138F, 0.0248F, 1.172F, -0.2758F, -0.2064F));

        PartDefinition cube_r35 = heel.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(11, 1).addBox(-0.875F, -0.2896F, -1.7796F, 1.0F, 0.25F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8172F, -1.6809F, 1.0202F, 1.0821F, 0.0F, 0.0F));

        PartDefinition left_back_plate = paladin_left_leg.addOrReplaceChild("left_back_plate", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.9577F, -2.6091F, -0.3992F, 0.0176F, 0.1396F, 0.0025F));

        PartDefinition cube_r36 = left_back_plate.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(10, 15).addBox(-0.4992F, -1.3226F, -1.0141F, 1.1F, 1.8F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1102F, -0.0456F, 0.1554F, -1.2481F, 1.3096F, -1.7332F));

        PartDefinition cube_r37 = left_back_plate.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(14, 7).addBox(-1.4049F, -3.0504F, -1.0618F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0656F, 1.5124F, -0.9327F, 1.139F, 1.3738F, 0.6715F));

        PartDefinition right_back_plate = paladin_left_leg.addOrReplaceChild("right_back_plate", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0423F, -2.6091F, -0.4992F, -0.0524F, 0.0F, 0.925F));

        PartDefinition cube_r38 = right_back_plate.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(6, 15).addBox(-1.0464F, -0.2329F, -1.6439F, 1.1F, 1.8F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3603F, 0.3674F, 0.2483F, -1.2481F, 1.3096F, -1.7332F));

        PartDefinition cube_r39 = right_back_plate.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(14, 5).addBox(-1.451F, -1.8319F, -1.6916F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5951F, 1.9253F, -0.8398F, 1.139F, 1.3738F, 0.6715F));

        PartDefinition back_bottom_plate = paladin_left_leg.addOrReplaceChild("back_bottom_plate", CubeListBuilder.create().texOffs(14, 11).addBox(-1.8717F, -2.2203F, -0.125F, 1.0F, 2.25F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6074F, -1.5679F, -1.125F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r40 = back_bottom_plate.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(14, 9).addBox(1.0214F, -0.2415F, -0.125F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8295F, -1.1023F, 0.0F, 3.1416F, 0.0F, -2.6791F));

        PartDefinition cube_r41 = back_bottom_plate.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(14, 13).addBox(-0.7588F, -2.3374F, -0.25F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0639F, -0.0138F, 0.125F, 0.0F, 0.0F, -0.4538F));

        PartDefinition bottom_left_leg = paladin_left_leg.addOrReplaceChild("bottom_left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(1.3F, -1.3F, -0.2F, -0.2182F, 0.0F, 0.0436F));

        PartDefinition back_collier = bottom_left_leg.addOrReplaceChild("back_collier", CubeListBuilder.create().texOffs(0, 0).addBox(-2.999F, -0.3574F, 0.0094F, 2.0F, 0.65F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(-1.3F, -5.9F, -2.1F));

        PartDefinition cube_r42 = back_collier.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 0).addBox(-0.6998F, -0.3574F, 0.7131F, 1.25F, 0.65F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r43 = back_collier.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(0, 0).addBox(-2.1631F, -0.3574F, -0.7998F, 1.45F, 0.65F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9293F, 0.0F, 0.0707F, 0.0F, 0.7854F, 0.0F));

        PartDefinition front_collier = bottom_left_leg.addOrReplaceChild("front_collier", CubeListBuilder.create(), PartPose.offset(-2.2912F, -5.675F, 0.999F));

        PartDefinition cube_r44 = front_collier.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(0, 0).addBox(1.1538F, -0.1824F, -1.6607F, 1.25F, 0.45F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, -3.1416F, 0.7854F, 3.1416F));

        PartDefinition cube_r45 = front_collier.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9535F, -0.1824F, -0.2604F, 1.2F, 0.45F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

        PartDefinition cube_r46 = front_collier.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(0, 0).addBox(0.2902F, -0.1824F, -0.3584F, 1.5F, 0.45F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bottom_leg = bottom_left_leg.addOrReplaceChild("bottom_leg", CubeListBuilder.create().texOffs(4, 7).addBox(-2.0986F, -0.7484F, -1.2924F, 1.3F, 5.2F, 1.8F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, -6.9F, 0.0F, 0.0349F, 0.0F, 0.0087F));

        PartDefinition cube_r47 = bottom_leg.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(4, 0).addBox(-1.8986F, -3.7306F, -0.3378F, 1.8F, 7.5F, 1.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.45F, -7.2903F, -2.4257F, 0.2793F, 0.0F, 0.0F));

        PartDefinition cube_r48 = bottom_leg.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(4, 0).addBox(-1.8986F, -3.7326F, -1.0848F, 1.8F, 7.5F, 1.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.45F, -7.2903F, -2.4257F, 0.4538F, 0.0F, 0.0F));

        PartDefinition bottom_back_plate = bottom_left_leg.addOrReplaceChild("bottom_back_plate", CubeListBuilder.create(), PartPose.offset(-1.35F, -4.7691F, -1.5951F));

        PartDefinition cube_r49 = bottom_back_plate.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(14, 0).addBox(-1.3466F, -1.4336F, 0.5162F, 1.3F, 2.75F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1476F, -0.7799F, 0.1042F));

        PartDefinition cube_r50 = bottom_back_plate.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(14, 2).addBox(-1.649F, -1.4336F, -1.0862F, 1.3F, 2.75F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0941F, 0.8951F, -0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r51 = bottom_back_plate.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(2, 14).addBox(-1.3662F, -1.4336F, -0.8966F, 1.3F, 2.75F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.1476F, 0.7799F, -0.1042F));

        PartDefinition cube_r52 = bottom_back_plate.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(8, 12).addBox(-0.691F, -4.7218F, 0.5323F, 1.3F, 5.3F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3678F, -1.4397F, -0.2961F, 0.2689F, -0.762F, -0.2132F));

        PartDefinition cube_r53 = bottom_back_plate.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(0, 7).addBox(-1.649F, -3.0564F, -1.3996F, 1.3F, 5.1F, 1.8F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.3309F, 0.6951F, 0.2094F, 0.0F, 0.0F));

        PartDefinition cube_r54 = bottom_back_plate.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(12, 4).addBox(-2.0066F, -4.5687F, -0.8944F, 1.3F, 5.2F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6585F, -1.5334F, -0.3216F, 0.2602F, 0.762F, 0.2132F));

        PartDefinition front_back_plate = bottom_left_leg.addOrReplaceChild("front_back_plate", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.35F, -4.1691F, 0.8049F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r55 = front_back_plate.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(10, 13).addBox(0.0529F, -1.4317F, -0.9099F, 1.3F, 2.2F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1476F, -0.7799F, 0.1042F));

        PartDefinition cube_r56 = front_back_plate.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(12, 13).addBox(0.349F, -1.4317F, -1.5049F, 1.3F, 2.2F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0941F, 0.8951F, -0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r57 = front_back_plate.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(0, 14).addBox(0.0599F, -1.4317F, 0.5029F, 1.3F, 2.2F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.1476F, 0.7799F, -0.1042F));

        PartDefinition cube_r58 = front_back_plate.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(8, 4).addBox(0.7883F, 0.3682F, -0.8159F, 1.3F, 6.0F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, -7.6448F, 0.9596F, -0.2864F, -0.762F, 0.2132F));

        PartDefinition cube_r59 = front_back_plate.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(0, 0).addBox(0.349F, -6.4751F, 0.1094F, 1.3F, 6.4F, 1.8F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.9448F, -0.5404F, -0.2269F, 0.0F, 0.0F));

        PartDefinition cube_r60 = front_back_plate.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(10, 4).addBox(-0.6159F, 0.024F, 0.3958F, 1.3F, 6.0F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -7.2448F, 1.0596F, -0.2864F, 0.762F, -0.2132F));

        PartDefinition paladin_right_leg = body.addOrReplaceChild("paladin_right_leg", CubeListBuilder.create(), PartPose.offset(-1.3F, 19.5F, 0.0F));

        PartDefinition top_front_shoe2 = paladin_right_leg.addOrReplaceChild("top_front_shoe2", CubeListBuilder.create(), PartPose.offset(-0.975F, -3.15F, 2.65F));

        PartDefinition cube_r61 = top_front_shoe2.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(0, 0).addBox(-0.275F, -0.0706F, -0.0428F, 0.5F, 0.4F, 1.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r62 = top_front_shoe2.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(0, 0).addBox(-0.275F, -0.25F, 0.0F, 0.5F, 0.4F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4F, -0.8F, -0.9F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r63 = top_front_shoe2.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(0, 0).addBox(-0.775F, -0.45F, -0.3F, 1.5F, 0.9F, 0.6F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4F, -0.8F, -1.2F, -0.7854F, 0.0F, 0.0F));

        PartDefinition front_shoe2 = paladin_right_leg.addOrReplaceChild("front_shoe2", CubeListBuilder.create(), PartPose.offset(-1.7F, -3.1F, 2.5F));

        PartDefinition cube_r64 = front_shoe2.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(0, 0).addBox(-0.0551F, 0.0167F, -1.06F, 0.75F, 0.2F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4F, 0.0F, 0.0F, -0.8513F, -0.1769F, -0.2395F));

        PartDefinition cube_r65 = front_shoe2.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(0, 0).addBox(-0.05F, 0.0F, -1.1F, 0.75F, 0.2F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4F, 0.7F, 0.9F, -0.6244F, -0.1769F, -0.2395F));

        PartDefinition cube_r66 = front_shoe2.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(0, 0).addBox(-0.7672F, -0.06F, -1.1201F, 0.75F, 0.1F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.9F, 0.1F, 0.0F, -0.8712F, 0.1667F, 0.2251F));

        PartDefinition cube_r67 = front_shoe2.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, 0.0F, -1.1F, 0.75F, 0.1F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.9F, 0.7F, 0.9F, -0.6269F, 0.1667F, 0.2251F));

        PartDefinition cube_r68 = front_shoe2.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(2, 0).addBox(-0.5F, -1.4F, -0.6F, 1.1F, 0.9F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.1F, 2.1F, 0.9F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r69 = front_shoe2.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(6, 0).addBox(-1.8F, -0.273F, -2.764F, 1.55F, 0.35F, 2.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.1F, 1.5F, -0.2F, -0.4102F, 0.0F, 0.0F));

        PartDefinition ankle2 = paladin_right_leg.addOrReplaceChild("ankle2", CubeListBuilder.create().texOffs(8, 0).addBox(3.55F, -5.2F, -1.15F, 1.5F, 2.3F, 2.4F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r70 = ankle2.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(4, 12).addBox(-0.5F, -2.0F, -1.05F, 0.55F, 2.1F, 1.85F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.3F, -3.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r71 = ankle2.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(8, 8).addBox(0.15F, -2.0F, -1.3F, 0.5F, 2.1F, 2.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4F, -3.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition right_front_plate2 = paladin_right_leg.addOrReplaceChild("right_front_plate2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.825F, -1.4274F, 0.4934F, 0.0083F, -0.1481F, -0.0529F));

        PartDefinition cube_r72 = right_front_plate2.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(0, 0).addBox(3.1334F, -2.1093F, -0.4501F, 0.25F, 0.85F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4417F, -2.3201F, -1.5002F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r73 = right_front_plate2.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(0, 0).addBox(5.2084F, 0.1756F, -0.0758F, 0.25F, 1.35F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -0.5818F, 2.3316F, -2.2078F, 0.0F, 0.0F));

        PartDefinition cube_r74 = right_front_plate2.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(0, 0).addBox(5.2084F, -0.5843F, -0.1001F, 0.25F, 1.8F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -0.6625F, 1.6487F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r75 = right_front_plate2.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(6, 12).addBox(5.2084F, -0.7576F, 0.2435F, 0.25F, 2.1F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -1.5008F, 1.5662F, -2.4522F, 0.0F, 0.0F));

        PartDefinition cube_r76 = right_front_plate2.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(0, 0).addBox(5.2084F, 0.1704F, -0.2312F, 0.25F, 1.35F, 0.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -2.1343F, 0.2059F, -1.2566F, 0.0F, 0.0F));

        PartDefinition cube_r77 = right_front_plate2.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(14, 3).addBox(3.1334F, -0.7779F, 1.0659F, 0.25F, 1.15F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4417F, -2.8694F, 0.973F, -2.5395F, 0.0F, 0.0F));

        PartDefinition cube_r78 = right_front_plate2.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(10, 10).addBox(5.2084F, 0.0701F, -1.1499F, 0.25F, 1.35F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6333F, -1.4411F, 0.7274F, -0.7418F, 0.0F, 0.0F));

        PartDefinition left_front_plate2 = paladin_right_leg.addOrReplaceChild("left_front_plate2", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.775F, -1.4274F, -0.8066F, 0.0083F, 0.1743F, 0.0532F));

        PartDefinition cube_r79 = left_front_plate2.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(0, 0).addBox(3.2289F, -3.8299F, -1.0536F, 0.25F, 0.85F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2567F, -2.3043F, -0.2264F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r80 = left_front_plate2.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(0, 0).addBox(5.3039F, -0.8876F, -1.5571F, 0.25F, 1.35F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -0.566F, 3.6054F, -2.2078F, 0.0F, 0.0F));

        PartDefinition cube_r81 = left_front_plate2.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(0, 0).addBox(5.3039F, -2.3049F, -0.7036F, 0.25F, 1.8F, 0.95F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -0.6467F, 2.9225F, -1.597F, 0.0F, 0.0F));

        PartDefinition cube_r82 = left_front_plate2.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(12, 8).addBox(5.3039F, -1.431F, -1.451F, 0.25F, 2.1F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -1.485F, 2.84F, -2.4522F, 0.0F, 0.0F));

        PartDefinition cube_r83 = left_front_plate2.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(0, 0).addBox(5.3039F, -1.6529F, -0.2257F, 0.25F, 1.35F, 0.75F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -2.1185F, 1.4797F, -1.2566F, 0.0F, 0.0F));

        PartDefinition cube_r84 = left_front_plate2.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(4, 14).addBox(3.2289F, -1.301F, -0.6808F, 0.25F, 1.15F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2567F, -2.8536F, 2.2469F, -2.5395F, 0.0F, 0.0F));

        PartDefinition cube_r85 = left_front_plate2.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(0, 11).addBox(5.3039F, -1.5142F, -0.2473F, 0.25F, 1.35F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1817F, -1.4253F, 2.0013F, -0.7418F, 0.0F, 0.0F));

        PartDefinition back_top_plate2 = paladin_right_leg.addOrReplaceChild("back_top_plate2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.7253F, -4.0415F, -1.7218F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r86 = back_top_plate2.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(0, 0).addBox(-0.0147F, -5.1103F, 1.4349F, 0.55F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1838F, 0.3791F, 0.475F, 0.2375F, -1.0086F, 1.603F));

        PartDefinition cube_r87 = back_top_plate2.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(0, 0).addBox(0.1775F, -5.3487F, 0.0836F, 0.95F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.264F, -1.0259F, 0.4133F, 0.3795F, -1.2254F, 1.446F));

        PartDefinition cube_r88 = back_top_plate2.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(0, 0).addBox(4.366F, -0.1377F, 0.0836F, 0.8F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -1.0F, 0.5F, 1.2497F, 0.0844F, 0.2481F));

        PartDefinition cube_r89 = back_top_plate2.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2575F, -5.4302F, -1.8044F, 0.55F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7238F, 0.4443F, 0.6251F, -0.3666F, -0.9083F, 1.6168F));

        PartDefinition cube_r90 = back_top_plate2.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(2, 7).addBox(-0.6739F, -5.5413F, -2.9079F, 1.05F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0947F, -0.8933F, 0.2594F, -0.6175F, -1.1801F, 1.9038F));

        PartDefinition cube_r91 = back_top_plate2.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(0, 0).addBox(4.4001F, -0.1377F, -2.9379F, 0.8F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9766F, -1.1936F, 0.5667F, 1.2535F, -0.0981F, -0.2897F));

        PartDefinition cube_r92 = back_top_plate2.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(11, 0).addBox(4.35F, -0.75F, -0.15F, 1.35F, 0.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1253F, 0.0915F, 0.7718F, 1.2392F, 0.0F, 0.0F));

        PartDefinition heel2 = paladin_right_leg.addOrReplaceChild("heel2", CubeListBuilder.create().texOffs(11, 11).addBox(3.7078F, -0.2059F, -0.8798F, 1.0F, 0.25F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1922F, -1.5441F, -0.3702F));

        PartDefinition cube_r93 = heel2.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(0, 0).addBox(-0.575F, -0.125F, 0.1F, 0.55F, 0.25F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.65F, 0.0F, 0.0F, 1.165F, 0.3207F, 0.1767F));

        PartDefinition cube_r94 = heel2.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(0, 0).addBox(0.025F, -0.125F, 0.1F, 0.55F, 0.25F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7718F, 0.0138F, 0.0248F, 1.172F, -0.2758F, -0.2064F));

        PartDefinition cube_r95 = heel2.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(11, 1).addBox(-0.875F, -0.2896F, -1.7796F, 1.0F, 0.25F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5828F, -1.6809F, 1.0202F, 1.0821F, 0.0F, 0.0F));

        PartDefinition left_back_plate2 = paladin_right_leg.addOrReplaceChild("left_back_plate2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.9577F, -2.6091F, -0.3992F, 0.0176F, 0.1396F, 0.0025F));

        PartDefinition cube_r96 = left_back_plate2.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(10, 15).addBox(-1.6244F, 1.382F, 4.6762F, 1.1F, 1.8F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1102F, -0.0456F, 0.1554F, -1.2481F, 1.3096F, -1.7332F));

        PartDefinition cube_r97 = left_back_plate2.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(14, 7).addBox(-1.3073F, -0.1227F, 4.6285F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0656F, 1.5124F, -0.9327F, 1.139F, 1.3738F, 0.6715F));

        PartDefinition right_back_plate2 = paladin_right_leg.addOrReplaceChild("right_back_plate2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0423F, -2.6091F, -0.4992F, -0.0524F, 0.0F, 0.925F));

        PartDefinition cube_r98 = right_back_plate2.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(6, 15).addBox(0.3519F, -2.7443F, 4.0743F, 1.1F, 1.8F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3603F, 0.3674F, 0.2483F, -1.2481F, 1.3096F, -1.7332F));

        PartDefinition cube_r99 = right_back_plate2.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(14, 5).addBox(-1.22F, -4.6971F, 4.0266F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5951F, 1.9253F, -0.8398F, 1.139F, 1.3738F, 0.6715F));

        PartDefinition back_bottom_plate2 = paladin_right_leg.addOrReplaceChild("back_bottom_plate2", CubeListBuilder.create().texOffs(14, 11).addBox(4.5283F, -2.2203F, -0.125F, 1.0F, 2.25F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6074F, -1.5679F, -1.125F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r100 = back_bottom_plate2.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(14, 9).addBox(-4.7062F, -3.0971F, -0.125F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8295F, -1.1023F, 0.0F, 3.1416F, 0.0F, -2.6791F));

        PartDefinition cube_r101 = back_bottom_plate2.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(14, 13).addBox(4.9935F, 0.4682F, -0.25F, 1.0F, 2.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0639F, -0.0138F, 0.125F, 0.0F, 0.0F, -0.4538F));

        PartDefinition bottom_left_leg2 = paladin_right_leg.addOrReplaceChild("bottom_left_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.3F, -1.1F, 0.0F, -0.2174F, -0.0189F, -0.0416F));

        PartDefinition back_collier2 = bottom_left_leg2.addOrReplaceChild("back_collier2", CubeListBuilder.create().texOffs(0, 0).addBox(3.395F, -0.7172F, -0.051F, 2.0F, 0.65F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(-1.3F, -5.7F, -2.3F));

        PartDefinition cube_r102 = back_collier2.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(0, 0).addBox(3.7788F, -0.7172F, -3.8509F, 1.25F, 0.65F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r103 = back_collier2.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(0, 0).addBox(2.4009F, -0.7172F, 3.6788F, 1.45F, 0.65F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9293F, 0.0F, 0.0707F, 0.0F, 0.7854F, 0.0F));

        PartDefinition front_collier2 = bottom_left_leg2.addOrReplaceChild("front_collier2", CubeListBuilder.create(), PartPose.offset(-2.2912F, -5.475F, 0.799F));

        PartDefinition cube_r104 = front_collier2.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(0, 0).addBox(-3.3247F, -0.5422F, 2.9032F, 1.25F, 0.45F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, -3.1416F, 0.7854F, 3.1416F));

        PartDefinition cube_r105 = front_collier2.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5174F, -0.5422F, -4.7389F, 1.2F, 0.45F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

        PartDefinition cube_r106 = front_collier2.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(0, 0).addBox(-6.1038F, -0.5422F, -0.298F, 1.5F, 0.45F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bottom_leg2 = bottom_left_leg2.addOrReplaceChild("bottom_leg2", CubeListBuilder.create().texOffs(4, 7).addBox(-1.6461F, 3.3724F, 0.4611F, 1.3F, 5.2F, 1.8F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0805F, -11.1728F, -2.1585F, 0.0349F, 0.0018F, -0.0436F));

        PartDefinition cube_r107 = bottom_leg2.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(4, 0).addBox(4.4952F, -4.1947F, -0.2524F, 1.8F, 7.5F, 1.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3913F, -2.6999F, -0.6263F, 0.2793F, 0.0F, 0.0F));

        PartDefinition cube_r108 = bottom_leg2.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(4, 0).addBox(4.4952F, -4.1748F, -0.9202F, 1.8F, 7.5F, 1.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3913F, -2.6999F, -0.6263F, 0.4538F, 0.0F, 0.0F));

        PartDefinition bottom_back_plate2 = bottom_left_leg2.addOrReplaceChild("bottom_back_plate2", CubeListBuilder.create(), PartPose.offset(-1.35F, -4.5691F, -1.7951F));

        PartDefinition cube_r109 = bottom_back_plate2.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(14, 0).addBox(3.1055F, -1.7851F, -4.0741F, 1.3F, 2.75F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1476F, -0.7799F, 0.1042F));

        PartDefinition cube_r110 = bottom_back_plate2.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(14, 2).addBox(4.745F, -1.7851F, -1.1839F, 1.3F, 2.75F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0941F, 0.8951F, -0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r111 = bottom_back_plate2.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(2, 14).addBox(3.2241F, -1.7851F, 3.5555F, 1.3F, 2.75F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.1476F, 0.7799F, -0.1042F));

        PartDefinition cube_r112 = bottom_back_plate2.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(8, 12).addBox(3.8436F, -4.9286F, -3.9856F, 1.3F, 5.3F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3678F, -1.4397F, -0.2961F, 0.2689F, -0.762F, -0.2132F));

        PartDefinition cube_r113 = bottom_back_plate2.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(0, 7).addBox(4.745F, -3.4209F, -1.3839F, 1.3F, 5.1F, 1.8F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.3309F, 0.6951F, 0.2094F, 0.0F, 0.0F));

        PartDefinition cube_r114 = bottom_back_plate2.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(12, 4).addBox(2.5013F, -5.1305F, 3.62F, 1.3F, 5.2F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6585F, -1.5334F, -0.3216F, 0.2602F, 0.762F, 0.2132F));

        PartDefinition front_back_plate2 = bottom_left_leg2.addOrReplaceChild("front_back_plate2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.35F, -3.9691F, 0.6049F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r115 = front_back_plate2.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(10, 13).addBox(-4.4524F, -1.7958F, 3.6272F, 1.3F, 2.2F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1476F, -0.7799F, 0.1042F));

        PartDefinition cube_r116 = front_back_plate2.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(12, 13).addBox(-6.045F, -1.7958F, -1.4825F, 1.3F, 2.2F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0941F, 0.8951F, -0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r117 = front_back_plate2.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(0, 14).addBox(-4.4772F, -1.7958F, -4.0024F, 1.3F, 2.2F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.1476F, 0.7799F, -0.1042F));

        PartDefinition cube_r118 = front_back_plate2.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(8, 4).addBox(-3.7463F, 0.0826F, 3.6977F, 1.3F, 6.0F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, -7.6448F, 0.9596F, -0.2864F, -0.762F, 0.2132F));

        PartDefinition cube_r119 = front_back_plate2.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(0, 0).addBox(-6.045F, -6.8393F, 0.0873F, 1.3F, 6.4F, 1.8F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.9448F, -0.5404F, -0.2269F, 0.0F, 0.0F));

        PartDefinition cube_r120 = front_back_plate2.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(10, 4).addBox(-5.1238F, -0.4194F, -4.1318F, 1.3F, 6.0F, 0.55F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -7.2448F, 1.0596F, -0.2864F, 0.762F, -0.2132F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wizard_hat = head.addOrReplaceChild("wizard_hat", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition wizard_hat_r1 = wizard_hat.addOrReplaceChild("wizard_hat_r1", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        return meshDefinition;
    }

    private static PartDefinition createEmptyPart(PartDefinition partdefinition, String name) {
        return partdefinition.addOrReplaceChild(name, CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0, CubeDeformation.NONE), PartPose.ZERO);
    }

    @Inject(method = "Lnet/minecraft/client/model/PlayerModel;setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
            at = @At("HEAD"), cancellable = true)
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {

        ci.cancel();

        boolean flag = entity.getFallFlyingTicks() > 4;
        boolean flag1 = entity.isVisuallySwimming();
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        if (flag) {
            this.head.xRot = (-(float) Math.PI / 4F);
        } else if (this.swimAmount > 0.0F) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float) Math.PI / 4F));
            } else {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, headPitch * ((float) Math.PI / 180F));
            }
        } else {
            this.head.xRot = headPitch * ((float) Math.PI / 180F);
        }

    }

}
