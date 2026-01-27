package dev.pswg.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import dev.pswg.Network;
import dev.pswg.NetworkTable;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.UUID;

public final class NetworkGlowingCommand {

	private final static String networkIdKeyPrivate = "networkId";

	private final static String commandName = "toggleNetworkGlow";

	public static final SuggestionProvider<ServerCommandSource> NETWORK_ID_SUGGESTER = (context, builder) -> {
		// Suppose you have a collection of active network UUIDs
		for (UUID id : NetworkTable.GetNetworkIds()) {
			builder.suggest(id.toString());
		}
		return builder.buildFuture();
	};

	public final static CommandRegistrationCallback Command = (dispatcher, registryAccess, environment) -> {
		dispatcher.register(
			CommandManager
				.literal(commandName)
		        .then(
					CommandManager
		                .argument(networkIdKeyPrivate, StringArgumentType.string())
                        .suggests(NETWORK_ID_SUGGESTER)
		                .executes(ctx -> {
		                    String idStr = StringArgumentType.getString(ctx, networkIdKeyPrivate);
		                    UUID networkId;
		                    try {
		                        networkId = UUID.fromString(idStr);
		                    } catch (IllegalArgumentException e) {
		                        ctx.getSource().sendFeedback(() -> Text.literal("Invalid UUID!"), false);
		                        return 0;
		                    }

		                    if(!Network.exists(networkId)){
		                        ctx.getSource().sendFeedback(() -> Text.literal("No such network with id " + networkId), false);
		                        return 0;
		                    }

		                    // Call your network glow trigger
		                    NetworkTable.ToggleNetworkGlow(networkId);

		                    ctx.getSource().sendFeedback(() -> Text.literal("Toggled glow for network: " + networkId), false);
		                    return 1;
		                })
		        )
		);
	};
}
