package safro.hover.pets.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.hover.pets.util.PacketHandler;


@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    
    @Inject(at = @At(value = "TAIL"), method = "onPlayerConnect")
    private void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        /*PlayerJoinCallback.EVENT.invoker().joinServer(player, player.getServer());
        if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME)) < 1) {
            PlayerFirstJoinCallback.EVENT.invoker().joinServerForFirstTime(player, player.getServer());
        }*/
        PacketHandler.sendPlayerNBT(player);
    }
}
