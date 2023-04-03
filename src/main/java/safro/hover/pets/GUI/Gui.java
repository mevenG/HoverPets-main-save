package safro.hover.pets.GUI;

import io.netty.buffer.Unpooled;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.*;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import safro.hover.pets.HoverPets;
import safro.hover.pets.HoverPetsClient;
import safro.hover.pets.util.IEntityDataSaver;
import safro.hover.pets.util.PetData;


import java.util.Map;

import static safro.hover.pets.registry.EntityRegistry.ENTITY_TYPES;

public class Gui extends BaseOwoScreen<FlowLayout> {

    public Gui() {
        //super(FlowLayout.class, BaseUIModelScreen.DataSource.file("gui.xml"));
    }
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {

        NbtCompound playerData = PetData.getPlayer(client.player);
        if(playerData == null){
            return;
        }
        HoverPets.LOGGER.info(HoverPetsClient.PLAYER_DATA);
        /*for (Map.Entry<EntityType<?>, Identifier> entry : ENTITY_TYPES.entrySet()) {
            String k = entry.getKey().toString();
            String name = k.substring(k.lastIndexOf('.') + 1);
            HoverPets.LOGGER.info(playerData);
            HoverPets.LOGGER.error(PetData.getPlayer(((IEntityDataSaver) client.player)));
            if(PetData.getPet(client.player,name).getInt("xp") >0){
                NbtCompound pet = PetData.getPet(client.player,name);
                int level = pet.getInt("level");
                HoverPets.LOGGER.warn(level);
                rootComponent.child(Components.label(toLvl(level)));
            }else{
                HoverPets.LOGGER.info(name);
                //HoverPets.LOGGER.info(client.player.getUuidAsString());
            }
        }*/
        //ItemComponent itm = Components.item(new ItemStack(CHICKEN_PET, 1));
        /*Text levelRow = Text.of(Text.translatable("entity.hoverpets.level").getString().formatted(Formatting.DARK_GRAY)+String.valueOf(PetData.getLevel((IEntityDataSaver) client.player)).formatted(Formatting.GREEN));
        Text xpRow = Text.of(String.valueOf(PetData.getXP((IEntityDataSaver) client.player)));
        Text petType = Text.of(String.valueOf(pet.getType()));*/
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.verticalFlow(Sizing.fill(50), Sizing.fill(50))
                        //.child(itm)
                        //.child(Components.label(Text.of(pet.getName().getString().formatted(Formatting.DARK_GRAY))))
                        //.child(Components.label(levelRow))
                        //.child(Components.label(xpRow))
                        //.child(Components.label(Text.translatable(pet.getPetStack().getTranslationKey()).formatted(Formatting.DARK_GRAY)))
                        .child(Components.button(Text.literal("A Button"), (ButtonComponent button) -> {
                            //button.parent().parent().removeChild()
                        }))
                        .padding(Insets.of(20)) //
                        .surface(Surface.PANEL)
                        .verticalAlignment(VerticalAlignment.TOP)
                        .horizontalAlignment(HorizontalAlignment.LEFT)
        );



        //rootComponent.child(Components.label(Text.translatable(pet.getPetStack().getTranslationKey()).formatted(Formatting.DARK_GRAY)));
        //rootComponent.childById(TextBoxComponent.class, "top-button").OnChanged(button -> boxContainer.verticalAlignment(VerticalAlignment.TOP));
        /*tb.onChanged((val) -> {
            HoverPets.LOGGER.info(val);
        });*/
    }


    @Override
    public void close() {
        //HoverPets.LOGGER.info(tb.getText());

        HoverPets.LOGGER.info("CLOSED");
        //PacketByteBuf packet = PacketByteBufs.create();
        //NbtCompound tag = new NbtCompound();
        //tag.putString("pet_name", tb.getText());
        //tag.putString("pet_name", "toto");
        //packet.writeNbt(tag);
        //ClientPlayNetworking.send(PacketHandler.SAVE_PET_MENU, packet);
        super.close();
    }

    private Text toLvl(int level){
        String lvl = "";
        for (int i = 0; i < 50; i++) {
            if (level < (i+1)*10) {
                lvl= lvl+"░"; // alt-176
            }
            else {
                lvl= lvl+"▓"; // alt-178
            }
        }
        return Text.of("["+lvl+"] "+level+" / 500");
    }

}











