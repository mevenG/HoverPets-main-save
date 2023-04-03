package safro.hover.pets.client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.client.model.ChickenPetModel;
import safro.hover.pets.util.PetUtils;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChickenPetRenderer extends GeoEntityRenderer<BasePetEntity> {
    public ChickenPetRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChickenPetModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureResource(BasePetEntity instance) {
        return new Identifier(HoverPets.MODID, "textures/entity/chicken_pet.png");
    }

    @Override
    public RenderLayer getRenderType(BasePetEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        //stack.translate(0.0D, 1.0f, 0.0D);
        stack.scale(1.8f, 1.8f, 1.8f);

        Text label = PetUtils.label(animatable);

        if (animatable.getOwner() != null && animatable.getOwner() instanceof PlayerEntity player) {
            if(animatable.hasCustomName()) {
                HoverPets.LOGGER.info(label);
                this.renderLabelIfPresent(animatable, label, stack, renderTypeBuffer, packedLightIn);
            }
        }
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}