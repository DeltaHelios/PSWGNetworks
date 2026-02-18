package dev.pswg.container;

import dev.pswg.Networks;
import dev.pswg.blockEntities.BasicNetworkComponentBlockEntity;
import dev.pswg.blockEntities.BasicNetworkCoreBlockEntity;
import dev.pswg.blockEntities.MiniTurretBlockEntity;
import dev.pswg.blocks.BasicNetworkComponentBlock;
import dev.pswg.blocks.BasicNetworkCoreBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings("NonFinalUtilityClass")
public class NetworksBlockEntities
{
	public static final BlockEntityType<BasicNetworkCoreBlockEntity> BASIC_NETWORK_CORE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			Networks.id(BasicNetworkCoreBlock.NAME),
			FabricBlockEntityTypeBuilder
					.create(BasicNetworkCoreBlockEntity::new, NetworksBlocks.BASIC_NETWORK_CORE_BLOCK)
					.build()
	);

	public static final BlockEntityType<BasicNetworkComponentBlockEntity> BASIC_NETWORK_COMPONENT_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			Networks.id(BasicNetworkComponentBlock.NAME),
			FabricBlockEntityTypeBuilder
					.create(BasicNetworkComponentBlockEntity::new, NetworksBlocks.BASIC_NETWORK_COMPONENT_BLOCK)
					.build()
	);

	public static final BlockEntityType<BasicNetworkComponentBlockEntity> MINI_TURRET_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			Networks.id("mini_turret_block_entity"),
			FabricBlockEntityTypeBuilder
					.create(BasicNetworkComponentBlockEntity::new, NetworksBlocks.MINI_TURRET_BLOCK)
					.build()
	);
	public static void register(){}
}
