package safro.hover.pets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import safro.hover.pets.command.RemovePetCommand;
import safro.hover.pets.registry.EntityRegistry;
import safro.hover.pets.registry.ItemRegistry;
import safro.hover.pets.registry.NetworkRegistry;
import safro.hover.pets.util.PacketHandler;
import safro.hover.pets.util.Config;
import software.bernie.geckolib3.GeckoLib;

public class HoverPets implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("hoverpets");
	public static final String MODID = "hoverpets";

	public static MinecraftServer server;

	public static JSONObject CONF;

	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(ItemRegistry.BLAZE_PET));

	@Override
	public void onInitialize() {
		//server  = (MinecraftServer) FabricLoader.getInstance().getGameInstance();
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			HoverPets.server = server;
		});
		EntityRegistry.init();
		ItemRegistry.init();
		NetworkRegistry.init();
		PacketHandler.registerS2CPacketHandlers();
		Config.init();
		GeckoLib.initialize();

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, env) -> RemovePetCommand.register(dispatcher));

	}

}
