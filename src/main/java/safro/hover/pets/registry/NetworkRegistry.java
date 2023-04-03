package safro.hover.pets.registry;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.util.PetData;

public class NetworkRegistry {
    public static final Identifier PET_ABILITY = new Identifier(HoverPets.MODID, "pet_ability");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(PET_ABILITY, ((server, player, handler, buf, responseSender) -> {
            if (PetData.hasPet(player)) {
                BasePetEntity pet = PetData.getPet(player);
                pet.onPetKey(player.world, player);
            }
        }));
    }
}
