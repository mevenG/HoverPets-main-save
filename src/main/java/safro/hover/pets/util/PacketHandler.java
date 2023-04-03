package safro.hover.pets.util;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import safro.hover.pets.HoverPets;
import safro.hover.pets.HoverPetsClient;
import safro.hover.pets.api.BasePetEntity;

import java.util.UUID;

import static safro.hover.pets.HoverPets.server;

public final class PacketHandler {
    public static final Identifier SAVE_PET_MENU = new Identifier("save_pet_menu");
    public static final Identifier GET_PLAYER_NBT = new Identifier("get_player_nbt");
    public static final Identifier SET_PET_DATA = new Identifier("pet_data");

    private PacketHandler() {
    }

    public static void registerS2CPacketHandlers() {
        //ServerPlayNetworking.registerGlobalReceiver(SAVE_PET_MENU, PacketHandler::save_pet_menu);
        ServerPlayNetworking.registerGlobalReceiver(GET_PLAYER_NBT, PacketHandler::sendPlayerNBT);
    }

    public static void registerC2SPacketHandlers() {
        ClientPlayNetworking.registerGlobalReceiver(GET_PLAYER_NBT, PacketHandler::ReceivePlayerNBT);
        ClientPlayNetworking.registerGlobalReceiver(SET_PET_DATA, PacketHandler::ReceivePetData);
    }


    public static void SendPetData(BasePetEntity pet) {
        PlayerManager playerManager = server.getPlayerManager();
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

        int level = PetData.getLevel(pet);
        //HoverPets.LOGGER.error(pet);
        NbtCompound tag = new NbtCompound();
        tag.putInt(pet.getUuidAsString(),level);
        buf.writeNbt(tag);

        playerManager.getPlayerList();
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(player, SET_PET_DATA, buf);
        }
    }
    public static void ReceivePetData(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        NbtCompound bufData = buf.readNbt();
        //HoverPets.LOGGER.error(bufData);
        NbtCompound mergedTag = new NbtCompound();
        mergedTag.copyFrom(HoverPetsClient.PET_DATA);
        mergedTag.copyFrom(bufData);
        HoverPetsClient.PET_DATA = mergedTag;
    }
    public static void ReceivePlayerNBT(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        NbtCompound playerData = buf.readNbt();
        HoverPetsClient.PLAYER_DATA = playerData;
    }





    public static void sendPlayerNBT(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        NbtCompound playerData = PetData.getPlayer(player);
        PacketByteBuf responseBuffer = new PacketByteBuf(Unpooled.buffer());
        responseBuffer.writeNbt(playerData);
        HoverPets.LOGGER.info(playerData);
        ServerPlayNetworking.send(player, GET_PLAYER_NBT, responseBuffer);
    }
    public static void sendPlayerNBT(ServerPlayerEntity player) {
        NbtCompound playerData = PetData.getPlayer(player);
        PacketByteBuf responseBuffer = new PacketByteBuf(Unpooled.buffer());
        responseBuffer.writeNbt(playerData);
        HoverPets.LOGGER.info(playerData);
        ServerPlayNetworking.send(player, GET_PLAYER_NBT, responseBuffer);
    }


    /*private static void save_pet_menu(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf data, PacketSender responseSender){
        NbtCompound tag = data.readNbt();
        server.execute(() -> {
            if (tag == null || !tag.contains("pet_name")) {
                return;
            }
            String petName = tag.getString("pet_name");
            if(PetData.hasPet(player)){
                IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                //PetData.removeThirst(dataPlayer,1);
                //player.sendMessage(Text.literal("Removed Thirst"));
                    BasePetEntity pet = PetUtil.getPet(player);
                    String oldPetName = pet.getpetName();
                    pet.setPetName(petName);
                    HoverPets.LOGGER.info(player.getName()+"renamed "+oldPetName+" to "+petName);
            }
        });
    }*/

}