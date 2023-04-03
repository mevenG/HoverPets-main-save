package safro.hover.pets.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.PetItem;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemRegistry {
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Item BLAZE_PET = register("blaze_pet", new PetItem(EntityRegistry.BLAZE_PET, simple()).setPerkTooltip("tooltip.hoverpets.blaze_pet"));
    public static final Item OCELOT_PET = register("ocelot_pet", new PetItem(EntityRegistry.OCELOT_PET, simple()).setPerkTooltip("tooltip.hoverpets.ocelot_pet"));
    public static final Item CHICKEN_PET = register("chicken_pet", new PetItem(EntityRegistry.CHICKEN_PET, simple()).setPerkTooltip("tooltip.hoverpets.chicken_pet"));
    public static final Item COW_PET = register("cow_pet", new PetItem(EntityRegistry.COW_PET, simple()).setPerkTooltip("tooltip.hoverpets.cow_pet"));
    public static final Item FOX_PET = register("fox_pet", new PetItem(EntityRegistry.FOX_PET, simple()).setPerkTooltip("tooltip.hoverpets.fox_pet"));
    public static final Item GLOW_SQUID_PET = register("glow_squid_pet", new PetItem(EntityRegistry.GLOW_SQUID_PET, simple()).setPerkTooltip("tooltip.hoverpets.glow_squid_pet"));
    public static final Item PUFFERFISH_PET = register("pufferfish_pet", new PetItem(EntityRegistry.PUFFERFISH_PET, simple()).setPerkTooltip("tooltip.hoverpets.pufferfish_pet"));
    public static final Item MAGMA_CUBE_PET = register("magma_cube_pet", new PetItem(EntityRegistry.MAGMA_CUBE_PET, simple()).setPerkTooltip("tooltip.hoverpets.magma_cube_pet"));
    public static final Item CREEPER_PET = register("creeper_pet", new PetItem(EntityRegistry.CREEPER_PET, simple()).setPerkTooltip("tooltip.hoverpets.creeper_pet"));
    public static final Item PANDA_PET = register("panda_pet", new PetItem(EntityRegistry.PANDA_PET, simple()).setPerkTooltip("tooltip.hoverpets.panda_pet"));
    public static final Item WITCH_PET = register("witch_pet", new PetItem(EntityRegistry.WITCH_PET, simple()).setPerkTooltip("tooltip.hoverpets.witch_pet"));
    public static final Item ENDERMAN_PET = register("enderman_pet", new PetItem(EntityRegistry.ENDERMAN_PET, simple()).setAbilityTooltip("tooltip.hoverpets.enderman_pet"));
    public static final Item SLIME_PET = register("slime_pet", new PetItem(EntityRegistry.SLIME_PET, simple()).setAbilityTooltip("tooltip.hoverpets.slime_pet"));

    public static Item.Settings simple() {
        return new Item.Settings().group(HoverPets.ITEMGROUP).maxCount(1);
    }

    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, new Identifier(HoverPets.MODID, name));
        return item;
    }

    public static void init() {
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
