package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.blocks.BasicNetworkComponentBlock;
import dev.pswg.blocks.BasicNetworkCoreBlock;
import dev.pswg.registry.Registrar;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

@SuppressWarnings("NonFinalUtilityClass")
public class NetworksBlocks
{
	public static final Block BASIC_NETWORK_CORE_BLOCK = Registrar.blockWithoutItem(
			Networks.id("basic_network_core"),
			BasicNetworkCoreBlock::new,
			AbstractBlock.Settings
                .create()
		        .strength(2.0F)
		        .requiresTool()
	);

	public static final Block BASIC_NETWORK_COMPONENT_BLOCK = Registrar.blockWithoutItem(
			Networks.id(BasicNetworkComponentBlock.NAME),
			BasicNetworkComponentBlock::new,
			AbstractBlock.Settings
					.create()
					.strength(2.0F)
					.requiresTool()
	);

	private static Block createBlock(String key, AbstractBlock.Settings settings)
	{
		return Registrar.block(Networks.id(key), Block::new, settings);
	}
	public static void register(){

	}
}
