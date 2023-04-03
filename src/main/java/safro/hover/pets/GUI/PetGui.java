package safro.hover.pets.GUI;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextBoxComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.util.IEntityDataSaver;
import safro.hover.pets.util.PacketHandler;
import safro.hover.pets.util.PetData;

public class PetGui extends BaseOwoScreen<FlowLayout> {

    TextBoxComponent tb;

    public PetGui() {
        //super(FlowLayout.class, BaseUIModelScreen.DataSource.file("gui.xml"));
    }
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        BasePetEntity pet = PetData.getPet(client.player);
        if(pet == null){
            PetData.setPet((IEntityDataSaver) client.player, "", "");
            return;
        }
        Text levelRow = Text.of(Text.translatable("entity.hoverpets.level").getString().formatted(Formatting.DARK_GRAY)+String.valueOf(PetData.getLevel((IEntityDataSaver) client.player)).formatted(Formatting.GREEN));
        Text xpRow = Text.of(String.valueOf(PetData.getXP((IEntityDataSaver) client.player)));
        Text petType = Text.of(String.valueOf(pet.getType()));
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.verticalFlow(Sizing.fill(50), Sizing.fill(50))
                        .child(Components.label(Text.of(pet.getName().getString().formatted(Formatting.DARK_GRAY))))
                        .child(Components.label(levelRow))
                        .child(Components.label(xpRow))
                        //.child(Components.label(Text.translatable(pet.getPetStack().getTranslationKey()).formatted(Formatting.DARK_GRAY)))
                        //.child(Components.button(Text.literal("A Button"), (ButtonComponent button) -> { System.out.println("click"); }))
                        //.child(tb)
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
        PacketByteBuf packet = PacketByteBufs.create();
        NbtCompound tag = new NbtCompound();
        //tag.putString("pet_name", tb.getText());
        tag.putString("pet_name", "toto");
        packet.writeNbt(tag);
        ClientPlayNetworking.send(PacketHandler.SAVE_PET_MENU, packet);
        super.close();
    }

}

