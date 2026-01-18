package dev.pswg.container;

import dev.pswg.networks.blockEntities.BasicNetworkCoreBlockEntity;
import dev.pswg.networks.blocks.BasicNetworkCoreBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

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
