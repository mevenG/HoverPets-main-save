package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class GlowSquidPet extends BasePetEntity {

    public static final String petType = "glow_squid_pet";
    public static String getPetType() {
        return petType;
    }

    public GlowSquidPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.GLOW_SQUID_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player, BasePetEntity basePetEntity) {
        this.world.addParticle(ParticleTypes.GLOW, this.getParticleX(0.6D), this.getRandomBodyY(), this.getParticleZ(0.6D), 0.0D, 0.0D, 0.0D);
    }
}
