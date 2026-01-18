package dev.pswg.items;

import dev.pswg.NetworkComponentPlacerItem;
import dev.pswg.container.NetworksBlocks;

public class BasicNetworkComponentItem extends NetworkComponentPlacerItem {
	public BasicNetworkComponentItem(Settings settings) {
		super(NetworksBlocks.BASIC_NETWORK_CORE_BLOCK, settings);
	}
}
