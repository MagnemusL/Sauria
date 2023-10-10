package net.smazeee.sauria.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.smazeee.sauria.Sauria;
import net.smazeee.sauria.entity.custom.AigialosaurusEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AigialosaurusModel extends AnimatedGeoModel<AigialosaurusEntity> {
    @Override
    public ResourceLocation getModelResource(AigialosaurusEntity object) {
        return new ResourceLocation(Sauria.MODID, "geo/aigialosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AigialosaurusEntity object) {
        return new ResourceLocation(Sauria.MODID, "textures/entity/aigialosaurus_texture_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AigialosaurusEntity animatable) {
        return new ResourceLocation(Sauria.MODID, "animations/aigialosaurus.animation.json");
    }
}
