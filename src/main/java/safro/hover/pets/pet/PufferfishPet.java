package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class PufferfishPet extends BasePetEntity {

    public static final String petType = "pufferfish_pet";
    public static String getPetType() {
        return petType;
    }

    public PufferfishPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.PUFFERFISH_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player, BasePetEntity basePetEntity) {
    }
}
