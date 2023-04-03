package safro.hover.pets.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.util.CompatUtil;
import safro.hover.pets.util.IEntityDataSaver;
import safro.hover.pets.util.PetData;

import java.util.Collection;

public class RemovePetCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("removePet")
                .requires(CompatUtil.canRunCommand())
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                        .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets")))));
    }

    private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> players) {
        for (ServerPlayerEntity player : players) {
            if (PetData.hasPet(player)) {
                if(PetData.getPet(player) != null){
                    PetData.getPet(player).remove(Entity.RemovalReason.DISCARDED);
                }

                PetData.setPet(((IEntityDataSaver) player) , "", "");
            }
        }

        if (players.size() == 1) {
            source.sendFeedback(Text.translatable("command.hoverpets.removepet", players.iterator().next().getDisplayName()), true);
        } else {
            source.sendFeedback(Text.translatable("command.hoverpets.removepets", players.size()), true);
        }
        return players.size();
    }
}
