package safro.hover.pets.util;

import net.minecraft.text.Text;
import safro.hover.pets.HoverPets;
import safro.hover.pets.HoverPetsClient;
import safro.hover.pets.api.BasePetEntity;

public class PetUtils {

    public static Text label(BasePetEntity entity){
        String name = entity.getName().getString();

        int lvl = HoverPetsClient.PET_DATA.getInt(entity.getUuidAsString());
        //HoverPets.LOGGER.info(lvl);
        String prefix = Text.translatable("entity.hoverpets.level").getString() + lvl + " ";
        return Text.of(prefix+name);
    }
}
