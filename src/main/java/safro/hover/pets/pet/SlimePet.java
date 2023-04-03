package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class SlimePet extends BasePetEntity {

    public static final String petType = "slime_pet";
    public static String getPetType() {
        return petType;
    }
    private int cooldown;

    public SlimePet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.SLIME_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player, BasePetEntity basePetEntity) {
        if (cooldown > Integer.MIN_VALUE) {
            --cooldown;
        }
    }

    @Override
    public void onPetKey(World world, PlayerEntity player) {
        if (cooldown < 1) {
            Vec3d vec = player.getRotationVector();
            player.setVelocity(0, 1.2F, 0);
            player.addVelocity(vec.x * 0.1F + (vec.x - player.getVelocity().x), 0, vec.z * 0.1F + (vec.z - player.getVelocity().z));
            player.velocityModified = true;
            player.fallDistance = 0;

            cooldown = 60;
        }
    }
}
