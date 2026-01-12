package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.registry.Registrar;
import net.minecraft.item.Item;

public class NetworksItems
{
	public static Item registerSimpleItem(String key)
	{
		return registerSimpleItem(key, new Item.Settings());
	}

	public static Item registerSimpleItem(String key, Item.Settings settings)
	{
		return Registrar.item(Networks.id(key), Item::new, settings);
	}
	public static void register()
	{

	}
}
