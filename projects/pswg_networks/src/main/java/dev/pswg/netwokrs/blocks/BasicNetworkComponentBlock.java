package dev.pswg.netwokrs.blocks;

import dev.pswg.NetworkComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class BasicNetworkComponentBlock extends NetworkComponent {
	public BasicNetworkComponentBlock(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}
