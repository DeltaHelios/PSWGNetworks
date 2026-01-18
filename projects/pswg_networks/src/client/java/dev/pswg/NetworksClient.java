package dev.pswg;

import dev.pswg.api.GalaxiesClientAddon;
import dev.pswg.networks.NetworkComponentPlacer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.UUID;

/**
 * The main entrypoint for PSWG client-side blaster features
 */
public class NetworksClient implements GalaxiesClientAddon
{

	@Override
	public void onGalaxiesClientReady()
	{
		ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
			Item item = stack.getItem();
			if (!(item instanceof NetworkComponentPlacer)) {
				return;
			}

			UUID networkId = stack.get(Networks.UUID_COMPONENT_TYPE);
			if (networkId == null) {
				return;
			}

			lines.add(Text.literal("Network: \"" + networkId + "\""));
		});
		Networks.LOGGER.info("Client module initialized");
	}

}
