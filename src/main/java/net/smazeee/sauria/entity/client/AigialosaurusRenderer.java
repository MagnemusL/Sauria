package net.smazeee.sauria.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.smazeee.sauria.Sauria;
import net.smazeee.sauria.entity.custom.AigialosaurusEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AigialosaurusRenderer extends GeoEntityRenderer<AigialosaurusEntity> {
    public AigialosaurusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AigialosaurusModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(AigialosaurusEntity instance) {
        return new ResourceLocation(Sauria.MODID, "textures/entity/aigialosaurus_texture_1.png");
    }

    @Override
    public RenderType getRenderType(AigialosaurusEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
