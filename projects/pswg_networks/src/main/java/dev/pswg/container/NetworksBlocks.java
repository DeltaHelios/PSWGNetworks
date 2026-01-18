package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.networks.blocks.BasicNetworkCoreBlock;
import dev.pswg.registry.Registrar;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public final class NetworksBlocks
{
	public static final Block BASIC_NETWORK_CORE = Registrar.block(
			BasicNetworkCoreBlock.ID,
			BasicNetworkCoreBlock::new,
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
