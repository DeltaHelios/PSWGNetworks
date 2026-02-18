package dev.pswg.container;

import dev.pswg.Network;
import dev.pswg.Networks;
import dev.pswg.blocks.*;
import dev.pswg.registry.Registrar;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

@SuppressWarnings("NonFinalUtilityClass")
public class NetworksBlocks
{
	public static final Block BASIC_NETWORK_CORE_BLOCK = Registrar.blockWithoutItem(
			Networks.id(BasicNetworkCoreBlock.NAME),
			BasicNetworkCoreBlock::new,
			AbstractBlock.Settings
                .create()
		        .strength(2.0F)
		        .requiresTool()
	);

	public static final Block BASIC_NETWORK_COMPONENT_BLOCK = Registrar.blockWithoutItem(
			Networks.id("basic_network_component_block"),
			BasicNetworkComponentBlock::new,
			AbstractBlock.Settings
					.create()
					.strength(2.0F)
					.requiresTool()
	);

	public static final Block BLAST_DOOR_BLOCK = Registrar.block(
			Networks.id("blast_door"),
			BlastDoorBlock::new,
			AbstractBlock.Settings.create()
					.hardness(1f)
					.luminance(state -> state.get(BlastDoorBlock.ACTIVATED) ? 15 : 0 )
	);

	public static final Block DATA_TERMINAL_BLOCK = Registrar.block(
			Networks.id("data_terminal"),
			DataTerminalBlock::new,
			AbstractBlock.Settings.create()
					.hardness(1f)
					.noCollision()
	);

	public static final Block MINI_TURRET_BLOCK = Registrar.block(
			Networks.id("mini_turret"),
			MiniTurretBlock::new,
			AbstractBlock.Settings.create()
					.hardness(1f)
					.dropsNothing()
	);

	private static Block createBlock(String key, AbstractBlock.Settings settings)
	{
		return Registrar.block(Networks.id(key), Block::new, settings);
	}
	public static void register(){

	}
}
