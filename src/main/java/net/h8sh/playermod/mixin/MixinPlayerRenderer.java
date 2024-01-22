package net.h8sh.playermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.capability.riding.Riding;
import net.h8sh.playermod.event.AnimationEvent;
import net.h8sh.playermod.event.ClientEvents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private MixinPlayerRenderer(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadow) {
        super(context, model, shadow);
    }

    private static HumanoidModel.ArmPose getArmPose(AbstractClientPlayer p_117795_, InteractionHand p_117796_) {
        ItemStack itemstack = p_117795_.getItemInHand(p_117796_);
        if (itemstack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        } else {
            if (p_117795_.getUsedItemHand() == p_117796_ && p_117795_.getUseItemRemainingTicks() > 0) {
                UseAnim useanim = itemstack.getUseAnimation();
                if (useanim == UseAnim.BLOCK) {
                    return HumanoidModel.ArmPose.BLOCK;
                }

                if (useanim == UseAnim.BOW) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                if (useanim == UseAnim.SPEAR) {
                    return HumanoidModel.ArmPose.THROW_SPEAR;
                }

                if (useanim == UseAnim.CROSSBOW && p_117796_ == p_117795_.getUsedItemHand()) {
                    return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                }

                if (useanim == UseAnim.SPYGLASS) {
                    return HumanoidModel.ArmPose.SPYGLASS;
                }

                if (useanim == UseAnim.TOOT_HORN) {
                    return HumanoidModel.ArmPose.TOOT_HORN;
                }

                if (useanim == UseAnim.BRUSH) {
                    return HumanoidModel.ArmPose.BRUSH;
                }
            } else if (!p_117795_.swinging && itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            HumanoidModel.ArmPose forgeArmPose = net.minecraftforge.client.extensions.common.IClientItemExtensions.of(itemstack).getArmPose(p_117795_, p_117796_, itemstack);
            if (forgeArmPose != null) return forgeArmPose;

            return HumanoidModel.ArmPose.ITEM;
        }
    }

    private static void isModelVisible(PlayerModel<AbstractClientPlayer> playermodel, String professionName, boolean flag) {
        var body = playermodel.body.getChild(professionName + "_body");
        var head = body.getChild(professionName + "_head");
        var chest = body.getChild(professionName + "_chest");
        var right_arm_full = body.getChild(professionName + "_right_arm_full");
        var left_arm_full = body.getChild(professionName + "_left_arm_full");
        var right_leg_full = body.getChild(professionName + "_right_leg_full");
        var left_leg_full = body.getChild(professionName + "_left_leg_full");
        chest.visible = flag;
        head.visible = flag;
        left_arm_full.visible = flag;
        right_leg_full.visible = flag;
        left_leg_full.visible = flag;
        right_arm_full.visible = flag;

    }

    private static void removeCustomHands(PlayerModel<AbstractClientPlayer> playermodel) {
        var wizard_body = playermodel.body.getChild("wizard_body");
        wizard_body.getChild("wizard_right_arm_full").visible = false;
        wizard_body.getChild("wizard_left_arm_full").visible = false;

       /* var paladin_body = playermodel.body.getChild("paladin_body");
        paladin_body.getChild("paladin_right_arm_full").visible = false;
        paladin_body.getChild("paladin_left_arm_full").visible = false;

        var berserk_body = playermodel.body.getChild("berserk_body");
        berserk_body.getChild("berserk_right_arm_full").visible = false;
        berserk_body.getChild("berserk_left_arm_full").visible = false;

        var rogue_body = playermodel.body.getChild("rogue_body");
        rogue_body.getChild("rogue_right_arm_full").visible = false;
        rogue_body.getChild("rogue_left_arm_full").visible = false;

        var invocator_body = playermodel.body.getChild("invocator_body");
        invocator_body.getChild("invocator_right_arm_full").visible = false;
        invocator_body.getChild("invocator_left_arm_full").visible = false;

        var druid_body = playermodel.body.getChild("druid_body");
        druid_body.getChild("druid_right_arm_full").visible = false;
        druid_body.getChild("druid_left_arm_full").visible = false;

        var fire_body = playermodel.body.getChild("fire_body");
        fire_body.getChild("fire_right_arm_full").visible = false;
        fire_body.getChild("fire_left_arm_full").visible = false;

        var aqua_body = playermodel.body.getChild("aqua_body");
        aqua_body.getChild("aqua_right_arm_full").visible = false;
        aqua_body.getChild("aqua_left_arm_full").visible = false;

        var wind_body = playermodel.body.getChild("wind_body");
        wind_body.getChild("wind_right_arm_full").visible = false;
        wind_body.getChild("wind_left_arm_full").visible = false;

        var spiritus_body = playermodel.body.getChild("spiritus_body");
        spiritus_body.getChild("spiritus_right_arm_full").visible = false;
        spiritus_body.getChild("spiritus_left_arm_full").visible = false;
*/

    }

    @Inject(
            method = {"renderRightHand"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void renderRightHand(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, CallbackInfo ci) {

        var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();
        if (currentProfession != Profession.Professions.BASIC) {
            ci.cancel();
            boolean shouldRenderHotBar = ClientEvents.getHotBar();
            PlayerModel<AbstractClientPlayer> playermodel = this.getModel();
            if (!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonArm(pMatrixStack, pBuffer, pCombinedLight, pPlayer, HumanoidArm.RIGHT) && shouldRenderHotBar)
                this.renderHand(pMatrixStack, pBuffer, pCombinedLight, pPlayer, playermodel.body.getChild(Profession.getProfessionName() + "_body").getChild(Profession.getProfessionName() + "_right_arm_full").getChild(Profession.getProfessionName() + "_right_forearm"), playermodel.body.getChild(Profession.getProfessionName() + "_body").getChild(Profession.getProfessionName() + "_right_arm_full").getChild(Profession.getProfessionName() + "_right_forearm"));
        }
    }

    @Inject(
            method = {"renderLeftHand"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void renderLeftHand(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, CallbackInfo ci) {

        var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();
        if (currentProfession != Profession.Professions.BASIC) {
            ci.cancel();
            boolean shouldRenderHotBar = ClientEvents.getHotBar();
            PlayerModel<AbstractClientPlayer> playermodel = this.getModel();
            if (!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonArm(pMatrixStack, pBuffer, pCombinedLight, pPlayer, HumanoidArm.LEFT) && shouldRenderHotBar)
                this.renderHand(pMatrixStack, pBuffer, pCombinedLight, pPlayer, playermodel.body.getChild(Profession.getProfessionName() + "_body").getChild(Profession.getProfessionName() + "_left_arm_full").getChild(Profession.getProfessionName() + "_left_forearm"), playermodel.body.getChild(Profession.getProfessionName() + "_body").getChild(Profession.getProfessionName() + "_left_arm_full").getChild(Profession.getProfessionName() + "_left_forearm"));
        }

    }

    @Shadow
    protected abstract void renderHand(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear);

    @Inject(
            method = {"setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V"},
            at = {@At("HEAD")},
            cancellable = true
    )
    protected void setupRotations(AbstractClientPlayer pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, CallbackInfo ci) {
        if (pEntityLiving.isDeadOrDying()) {
            ci.cancel();
        }
    }

    @Inject(
            method = {"setModelProperties"},
            at = {@At("RETURN")},
            cancellable = true
    )
    private void setModelProperties(AbstractClientPlayer p_117819_, CallbackInfo ci) {
        ci.cancel();

        PlayerModel<AbstractClientPlayer> playermodel = this.getModel();
        AnimationEvent.setPlayermodel(playermodel);

        if (p_117819_.isSpectator()) {
            playermodel.setAllVisible(false);
            playermodel.body.getChild("steve_body").getChild("steve_head").visible = true;
            playermodel.hat.visible = true;
        } else {

            // Profession ----------------------------------------------------------------------------------------------

            var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();

            switch (currentProfession) {

                case BASIC -> {
                    removeCustomHands(playermodel);
                    isModelVisible(playermodel, "steve", true);
                    isModelVisible(playermodel, "wizard", false);
                    /*isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case WIZARD -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", true);
  /*                  isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case ROGUE -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
              /*      isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", true);*/
                }
                case BERSERK -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
          /*          isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", true);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case INVOCATOR -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
               /*     isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", true);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case FIREMETA -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
              /*      isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", true);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case AQUAMETA -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
     /*               isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", true);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case SPIRITUSMETA -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
               /*     isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", true);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case PALADIN -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
               /*     isModelVisible(playermodel, "paladin", true);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case WINDMETA -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
                /*    isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", false);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", true);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
                case DRUID -> {
                    isModelVisible(playermodel, "steve", false);
                    isModelVisible(playermodel, "wizard", false);
             /*       isModelVisible(playermodel, "paladin", false);
                    isModelVisible(playermodel, "berserk", false);
                    isModelVisible(playermodel, "druid", true);
                    isModelVisible(playermodel, "invocator", false);
                    isModelVisible(playermodel, "fire", false);
                    isModelVisible(playermodel, "aqua", false);
                    isModelVisible(playermodel, "wind", false);
                    isModelVisible(playermodel, "spiritus", false);
                    isModelVisible(playermodel, "rogue", false);*/
                }
            }

            // Travel --------------------------------------------------------------------------------------------------

            //TODO

            var currentMount = Riding.getRiding();

            // Rendering -----------------------------------------------------------------------------------------------

            playermodel.setAllVisible(true);
            playermodel.hat.visible = p_117819_.isModelPartShown(PlayerModelPart.HAT);
            playermodel.jacket.visible = p_117819_.isModelPartShown(PlayerModelPart.JACKET);
            playermodel.leftPants.visible = p_117819_.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
            playermodel.rightPants.visible = p_117819_.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
            playermodel.leftSleeve.visible = p_117819_.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
            playermodel.rightSleeve.visible = p_117819_.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
            playermodel.crouching = p_117819_.isCrouching();

            HumanoidModel.ArmPose humanoidmodel$armpose = getArmPose(p_117819_, InteractionHand.MAIN_HAND);
            HumanoidModel.ArmPose humanoidmodel$armpose1 = getArmPose(p_117819_, InteractionHand.OFF_HAND);

            if (humanoidmodel$armpose.isTwoHanded()) {
                humanoidmodel$armpose1 = p_117819_.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }

            if (p_117819_.getMainArm() == HumanoidArm.RIGHT) {
                playermodel.rightArmPose = humanoidmodel$armpose;
                playermodel.leftArmPose = humanoidmodel$armpose1;
            } else {
                playermodel.rightArmPose = humanoidmodel$armpose1;
                playermodel.leftArmPose = humanoidmodel$armpose;
            }
        }
    }

    @Inject(
            method = {"Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;"},
            at = {@At("RETURN")},
            cancellable = true
    )
    public void getTextureLocation(AbstractClientPlayer p_117783_, CallbackInfoReturnable<ResourceLocation> cir) {
        // Profession texture ------------------------------------------------------------------------------------------

        var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();

        if (SmokeCapability.getOnSmoke() && Profession.getProfession() == Profession.Professions.ROGUE) {
            cir.setReturnValue(Profession.getAbilityTexture("smoke"));
        } else {
            cir.setReturnValue(Profession.getProfessionTexture(currentProfession.getId()));
        }
    }


}


