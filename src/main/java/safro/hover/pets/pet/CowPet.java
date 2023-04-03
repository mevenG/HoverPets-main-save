package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

import java.util.ArrayList;

public class CowPet extends BasePetEntity {

    public static final String petType = "cow_pet";
    public static String getPetType() {
        return petType;
    }

    public CowPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.COW_PET);
    }

    private int cooldown;

    @Override
    public void tickPerk(World world, PlayerEntity player, BasePetEntity basePetEntity) {
        --cooldown;
        if (cooldown < 1) {
            if (!world.isClient) {
                ArrayList<StatusEffect> negatives = new ArrayList<>();
                player.getActiveStatusEffects().forEach((effect, instance) -> {
                    if(effect.getCategory().equals(StatusEffectCategory.HARMFUL)) {
                        negatives.add(effect);
                    }
                });

                for(StatusEffect effect: negatives) {
                    player.removeStatusEffect(effect);
                }
                player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
            }
            cooldown = 1200;
        }
    }
}
