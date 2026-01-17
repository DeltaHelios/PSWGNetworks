package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.netwokrs.items.BasicNetworkCoreItem;
import dev.pswg.registry.Registrar;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.function.Function;

public final class NetworksItems
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
	public static void register() {

	}

	public static HashSet<Item> AllItems = new HashSet<>();

	public static final Item BASIC_NETWORK_CORE = registerItem(
			"basic_network_core",
             BasicNetworkCoreItem::new,
             new Item.Settings()
	);
}
