package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.blocks.BasicNetworkComponentBlock;
import dev.pswg.blocks.BasicNetworkCoreBlock;
import dev.pswg.items.BasicNetworkComponentItem;
import dev.pswg.items.BasicNetworkCoreItem;
import dev.pswg.registry.Registrar;
import net.minecraft.item.Item;

import java.util.function.Function;

@SuppressWarnings("NonFinalUtilityClass")
public class NetworksItems
{
	public static Item registerSimpleItem(String key)
	{
		return registerSimpleItem(key, new Item.Settings());
	}

	public static Item registerSimpleItem(String key, Item.Settings settings) {
		return registerItem(key, Item::new, settings);
	}

	private static Item registerItem(String key, Function<Item.Settings, Item> factory, Item.Settings settings) {
		return Registrar.item(Networks.id(key), factory, settings);
	}
	public static void register() {}

	public static final Item BASIC_NETWORK_CORE_ITEM = registerItem(
			BasicNetworkCoreBlock.NAME,
			BasicNetworkCoreItem::new,
			new Item.Settings()
	);

	public static final Item BASIC_NETWORK_COMPONENT_ITEM = registerItem(
			BasicNetworkComponentBlock.NAME,
			BasicNetworkComponentItem::new,
			new Item.Settings()
	);
}
