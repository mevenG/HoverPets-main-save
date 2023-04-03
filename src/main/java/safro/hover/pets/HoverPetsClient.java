package safro.hover.pets;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import safro.hover.pets.registry.ClientRegistry;
import safro.hover.pets.util.PacketHandler;

import static safro.hover.pets.util.PacketHandler.GET_PLAYER_NBT;
import static safro.hover.pets.util.PetData.addXP;

public class HoverPetsClient implements ClientModInitializer {

    public static NbtCompound PLAYER_DATA = new NbtCompound();

    public static NbtCompound PET_DATA = new NbtCompound();
    public static KeyBinding PET_KEY = new KeyBinding("key.hoverpets.pet_ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "category.hoverpets.hoverpets");
    private boolean pressed = false;

    @Override
    public void onInitializeClient() {



        ClientRegistry.init();
        PacketHandler.registerC2SPacketHandlers();
        //ClientPlayNetworking.send(GET_PLAYER_NBT, new PacketByteBuf(Unpooled.buffer()));

        KeyBindingHelper.registerKeyBinding(PET_KEY);


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (PET_KEY.wasPressed()) {
                if (!pressed) {
                    ClientPlayNetworking.send(GET_PLAYER_NBT, new PacketByteBuf(Unpooled.buffer()));
                    HoverPets.LOGGER.error(PLAYER_DATA);
                    //client.setScreen(new Gui());
                    /*if(!PetData.hasPet(client.player)) {
                        HoverPets.LOGGER.info(PetData.hasPet(client.player));

                        return;
                    }else {
                        PetData.addXP((IEntityDataSaver) client.player, 43);
                        client.setScreen(new Gui());
                    }*/
                }
                pressed = true;
            } else
                pressed = false;
        });

    }


}

