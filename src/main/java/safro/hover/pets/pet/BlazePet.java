package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;
import software.bernie.geckolib3.core.manager.AnimationData;

public class BlazePet extends BasePetEntity {


    public static final String petType = "blaze_pet";
    public ItemStack itemStack = new ItemStack(ItemRegistry.BLAZE_PET);

    public BlazePet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    public void tickPerk(World world, PlayerEntity player, BasePetEntity basePetEntity) {
        if (player.isOnFire()) {
            player.extinguish();
        }
    }

    public ItemStack getPetStack() {
        return itemStack;
    }

    public static String getPetType() {
        return petType;
    }


    @Override
    public void registerControllers(AnimationData animationData) {

    }
}
