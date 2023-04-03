package safro.hover.pets.client.model;

import net.minecraft.util.Identifier;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.BasePetEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChickenPetModel extends AnimatedGeoModel<BasePetEntity> {
    @Override
    public Identifier getModelResource(BasePetEntity object) {
        return new Identifier(HoverPets.MODID, "geo/chicken_pet.geo.json");
    }

    @Override
    public Identifier getTextureResource(BasePetEntity object) {
        return new Identifier(HoverPets.MODID, "textures/entity/chicken_pet.png");
    }

    @Override
    public Identifier getAnimationResource(BasePetEntity animatable) {
        return new Identifier(HoverPets.MODID, "animations/chicken_idle.json");
    }
}
