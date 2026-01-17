package dev.pswg;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import org.jetbrains.annotations.NotNull;

public abstract class NetworkComponent extends BlockEntity {

	public NetworkNode node;



	public NetworkComponent(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);

		if(world == null){
			throw new IllegalStateException(
					"World is null during NetworkCoreBlockEntity construction. " +
					"Network creation requires a valid World instance."
			);
		}

		node = NetworkNode.Create(new GlobalPos(world.getRegistryKey(), pos));
	}
}
