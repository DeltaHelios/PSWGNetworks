package dev.pswg.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.pswg.Network;
import dev.pswg.NetworkNode;
import dev.pswg.NetworkTable;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public final class NetworkDebugCommand {
	private final static String networkIdKeyPrivate = "networkId";

	private final static String commandName = "debugNetwork";

	public final static CommandRegistrationCallback Command = (dispatcher, registryAccess, environment) -> {
		dispatcher.register(
			CommandManager
				.literal(commandName)
				.then(
					CommandManager
						.argument(networkIdKeyPrivate, StringArgumentType.string())
							.suggests(NetworkGlowingCommand.NETWORK_ID_SUGGESTER)
						.executes(ctx -> {
							String idStr = StringArgumentType.getString(ctx, networkIdKeyPrivate);
							UUID networkId;
							try {
								networkId = UUID.fromString(idStr);
							} catch (IllegalArgumentException e) {
								Print(ctx,"Invalid UUID!");
								return 0;
							}

							if(!Network.exists(networkId)){
								Print(ctx,"No such network with id " + networkId);
								return 0;
							}

							for (NetworkNode node : NetworkTable.GetNetwork(networkId).GetNodes()){
								NetworkTable.GetComponent(node).ifPresent(component -> {

									BlockPos componentPos =  component.getPos();
									@Nullable World world =  component.getWorld();

									Print(ctx, "X: " + componentPos.getX() + ", Y: " + componentPos.getY() + ", Z: " + componentPos.getZ());

									if(world == null){
										Print(ctx, "    World: null");
										Print(ctx, "    Block Name: Unreachable");
									}
									else{
										Print(ctx, "    World: is not null");
										Print(ctx, "    Block Name: " + world.getBlockState(componentPos).getBlock().getName());
									}
									Print(ctx, "    Glowing: " + component.Glowing);
									Print(ctx, "    Shape exists: " + (component.Shape != null));
								});

							}

							return 1;

						})
				)
		);
	};

	private static void Print(CommandContext<ServerCommandSource> ctx, String message){
		ctx.getSource().sendFeedback(() -> Text.literal(message), false);
	}

}
