package net.h8sh.playermod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.capability.riding.Riding;
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

    private static String armToDraw(Profession.Professions currentProfession, boolean isLeftArm) {
        if (isLeftArm) {
            //return currentProfession.getName() + "_left_arm";
            return "wizard_left_arm";
        } else {
            //return currentProfession.getName() + "_right_arm";
            return "wizard_right_arm";
        }
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
                this.renderHand(pMatrixStack, pBuffer, pCombinedLight, pPlayer, playermodel.body.getChild(armToDraw(currentProfession, false)), playermodel.body.getChild(armToDraw(currentProfession, false)));
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
                this.renderHand(pMatrixStack, pBuffer, pCombinedLight, pPlayer, playermodel.body.getChild(armToDraw(currentProfession, true)), playermodel.body.getChild(armToDraw(currentProfession, true)));
        }

    }

    @Shadow
    protected abstract void renderHand(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear);


    @Inject(
            method = {"setModelProperties"},
            at = {@At("RETURN")},
            cancellable = true
    )
    private void setModelProperties(AbstractClientPlayer p_117819_, CallbackInfo ci) {
        ci.cancel();

        PlayerModel<AbstractClientPlayer> playermodel = this.getModel();
        if (p_117819_.isSpectator()) {
            playermodel.setAllVisible(false);
            playermodel.head.visible = true;
            playermodel.hat.visible = true;
        } else {

            // Profession ----------------------------------------------------------------------------------------------

            var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();

            switch (currentProfession) {

                case BASIC -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case ROGUE -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case BERSERK -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case INVOCATOR -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case FIREMETA -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case AQUAMETA -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case WIZARD -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case SPIRITUSMETA -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case PALADIN -> {
                    playermodel.body.getChild("paladin_right_leg").visible = true;
                    playermodel.body.getChild("paladin_left_leg").visible = true;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = false;
                    playermodel.body.getChild("druid_left_leg").visible = false;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
                case WINDMETA -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = false;
                    playermodel.body.getChild("druid_right_arm").visible = false;
                    playermodel.body.getChild("druid_left_arm").visible = false;
                    playermodel.body.getChild("druid_right_leg").visible = false;
                    playermodel.body.getChild("druid_left_leg").visible = false;
                    playermodel.body.getChild("wizard_right_arm").visible = true;
                    playermodel.body.getChild("wizard_left_arm").visible = true;
                    playermodel.body.getChild("wizard_right_leg").visible = true;
                    playermodel.body.getChild("wizard_left_leg").visible = true;
                    playermodel.head.getChild("wizard_hat").visible = true;
                }
                case DRUID -> {
                    playermodel.body.getChild("paladin_right_leg").visible = false;
                    playermodel.body.getChild("paladin_left_leg").visible = false;
                    playermodel.body.getChild("druid_tail").visible = true;
                    playermodel.body.getChild("druid_right_arm").visible = true;
                    playermodel.body.getChild("druid_left_arm").visible = true;
                    playermodel.body.getChild("druid_right_leg").visible = true;
                    playermodel.body.getChild("druid_left_leg").visible = true;
                    playermodel.body.getChild("wizard_right_arm").visible = false;
                    playermodel.body.getChild("wizard_left_arm").visible = false;
                    playermodel.body.getChild("wizard_right_leg").visible = false;
                    playermodel.body.getChild("wizard_left_leg").visible = false;
                    playermodel.head.getChild("wizard_hat").visible = false;
                }
            }

            // Riding --------------------------------------------------------------------------------------------------

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

    // Riding ------------------------------------------------------------------------------------------------------

    //TODO: add textures to asset folder

        /*var currentMount = Riding.getRiding();

        cir.setReturnValue(Riding.getRidingTexture(currentMount))

        }*/


}


