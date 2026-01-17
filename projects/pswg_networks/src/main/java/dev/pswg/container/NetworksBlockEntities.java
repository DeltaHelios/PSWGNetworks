package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.netwokrs.blockEntities.BasicNetworkCoreBlockEntity;
import dev.pswg.netwokrs.blocks.BasicNetworkCoreBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class NetworksBlockEntities
{
	public static final BlockEntityType<BasicNetworkCoreBlockEntity> BASIC_NETWORK_CORE = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			BasicNetworkCoreBlock.ID,
			FabricBlockEntityTypeBuilder
					.create(BasicNetworkCoreBlockEntity::new, NetworksBlocks.BASIC_NETWORK_CORE)
					.build()
	);
	public static void register()
	{
	}
}
