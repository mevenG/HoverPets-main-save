package safro.hover.pets.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.hover.pets.HoverPets;
import safro.hover.pets.pet.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityRegistry {
    public static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static final EntityType<BlazePet> BLAZE_PET = register(BlazePet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, BlazePet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<OcelotPet> OCELOT_PET = register(OcelotPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, OcelotPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<ChickenPet> CHICKEN_PET = register(ChickenPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, ChickenPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<CowPet> COW_PET = register(CowPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, CowPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<FoxPet> FOX_PET = register(FoxPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, FoxPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<GlowSquidPet> GLOW_SQUID_PET = register(GlowSquidPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, GlowSquidPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<PufferfishPet> PUFFERFISH_PET = register(PufferfishPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, PufferfishPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<MagmaCubePet> MAGMA_CUBE_PET = register(MagmaCubePet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, MagmaCubePet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<CreeperPet> CREEPER_PET = register(CreeperPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, CreeperPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<PandaPet> PANDA_PET = register(PandaPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, PandaPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<WitchPet> WITCH_PET = register(WitchPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, WitchPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<EndermanPet> ENDERMAN_PET = register(EndermanPet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, EndermanPet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<SlimePet> SLIME_PET = register(SlimePet.getPetType(), FabricEntityTypeBuilder.create(SpawnGroup.MISC, SlimePet::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        ENTITY_TYPES.put(type, new Identifier(HoverPets.MODID, name));
        return type;
    }

    public static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));

        FabricDefaultAttributeRegistry.register(BLAZE_PET, BlazePet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(OCELOT_PET, OcelotPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(CHICKEN_PET, ChickenPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(COW_PET, CowPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(FOX_PET, FoxPet.createFoxPetAttributes());
        FabricDefaultAttributeRegistry.register(GLOW_SQUID_PET, GlowSquidPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(PUFFERFISH_PET, PufferfishPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(MAGMA_CUBE_PET, MagmaCubePet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(CREEPER_PET, CreeperPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(PANDA_PET, PandaPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(WITCH_PET, WitchPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(ENDERMAN_PET, EndermanPet.createPetAttributes());
        FabricDefaultAttributeRegistry.register(SLIME_PET, SlimePet.createPetAttributes());
    }
}
