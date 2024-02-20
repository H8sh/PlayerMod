package net.h8sh.playermod.entity.client.pet;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.entity.custom.pet.SwouiffiEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SwouiffiModel extends GeoModel<SwouiffiEntity> {

    @Override
    public ResourceLocation getModelResource(SwouiffiEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "geo/swouiffi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SwouiffiEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "textures/entity/swouiffi.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SwouiffiEntity animatable) {
        return new ResourceLocation(PlayerMod.MODID, "animations/swouiffi.animation.json");
    }

    @Override
    public void setCustomAnimations(SwouiffiEntity animatable, long instanceId, AnimationState<SwouiffiEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}
