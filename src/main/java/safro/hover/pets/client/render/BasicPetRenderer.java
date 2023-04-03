package safro.hover.pets.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import safro.hover.pets.HoverPets;
import safro.hover.pets.HoverPetsClient;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.client.model.BasicPetModel;
import safro.hover.pets.registry.ClientRegistry;
import safro.hover.pets.util.IEntityDataSaver;

import static safro.hover.pets.util.PetUtils.label;

public class BasicPetRenderer extends LivingEntityRenderer<BasePetEntity, BasicPetModel> {

    public BasicPetRenderer(EntityRendererFactory.Context context) {
        super(context, new BasicPetModel(context.getPart(ClientRegistry.PET_LAYER)), 0.1F);
    }

    @Override
    public Identifier getTexture(BasePetEntity entity) {
        return new Identifier(HoverPets.MODID, "textures/entity/" + Registry.ENTITY_TYPE.getId(entity.getType()).getPath() + ".png");
    }

    @Override
    public void render(BasePetEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        //matrixStack.translate(0.0D, 1.5F / 2.0F, 0.0D);
        if (entity.getOwner() != null && entity.getOwner() instanceof PlayerEntity player) {
            if(entity.hasCustomName()) {
                this.renderLabelIfPresent(entity, label(entity), matrixStack, vertexConsumerProvider, i);
            }
        }
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected boolean hasLabel(BasePetEntity entity) {
        return false;
    }

}
