package dev.pswg.blockEntities;

import dev.pswg.NetworkComponentBlockEntity;
import dev.pswg.container.NetworksBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class BasicNetworkComponentBlockEntity extends NetworkComponentBlockEntity {
	public BasicNetworkComponentBlockEntity(BlockPos pos, BlockState state) {
		super(NetworksBlockEntities.BASIC_NETWORK_COMPONENT_BLOCK_ENTITY, pos, state);
	}
}
