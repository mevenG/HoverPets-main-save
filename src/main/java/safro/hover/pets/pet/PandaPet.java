package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class PandaPet extends BasePetEntity {

    public static final String petType = "panda_pet";
    public static String getPetType() {
        return petType;
    }
    private int cooldown;

    public PandaPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.PANDA_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player, BasePetEntity basePetEntity) {
        --cooldown;
        if (cooldown < 1) {
            if (player.canConsume(false)) {
                player.getHungerManager().add(8, 0.8F);
            }
            cooldown = 1200 + random.nextInt(600);
        }
    }
}
