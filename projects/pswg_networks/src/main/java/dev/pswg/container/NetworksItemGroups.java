package dev.pswg.container;

import dev.pswg.Galaxies;
import dev.pswg.Networks;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;



@SuppressWarnings("NonFinalUtilityClass")
public class NetworksItemGroups
{
	public static final RegistryKey<ItemGroup> NETWORKS_GROUP_KEY = registerGroup("networks");
	public static final ItemGroup NETWORKS_GROUP = FabricItemGroup
			.builder()
			.icon(() -> new ItemStack(Blocks.BLUE_STAINED_GLASS))
			.displayName(Text.translatable("pswg_networks.networks_group"))
			.build();

	private static RegistryKey<ItemGroup> registerGroup(String id)
	{
		return RegistryKey.of(RegistryKeys.ITEM_GROUP, Networks.id(id));
	}

	public static void register()
	{
		Registry.register(Registries.ITEM_GROUP, NETWORKS_GROUP_KEY, NETWORKS_GROUP);

		ItemGroupEvents.modifyEntriesEvent(NETWORKS_GROUP_KEY)
		               .register(itemGroup -> itemGroup.add(Blocks.BLUE_STAINED_GLASS));
	}
}
