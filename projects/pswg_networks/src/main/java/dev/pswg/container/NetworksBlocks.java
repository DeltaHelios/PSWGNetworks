package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.registry.Registrar;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public class NetworksBlocks
{
	private static Block createBlock(String key, AbstractBlock.Settings settings)
	{
		return Registrar.block(Networks.id(key), Block::new, settings);
	}
	public static void register(){

	}
}
