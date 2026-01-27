package dev.pswg;

import dev.pswg.api.GalaxiesClientAddon;
import dev.pswg.container.NetworksBlockEntities;
import dev.pswg.util.NetworkComponentBlockEntityRenderState;
import dev.pswg.util.NetworkComponentBlockEntityRenderer;
import dev.pswg.util.NetworkComponentBlockEntityRendererFactory;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
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
			if (!(item instanceof NetworkComponentPlacerItem)) {
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
