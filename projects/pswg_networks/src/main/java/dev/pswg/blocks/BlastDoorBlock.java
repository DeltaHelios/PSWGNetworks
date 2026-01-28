package dev.pswg.blocks;

import com.mojang.serialization.MapCodec;
import dev.pswg.NetworkBlock;
import dev.pswg.NetworkComponentBlockEntity;
import dev.pswg.container.NetworksBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.util.math.BlockPos;

public class BlastDoorBlock extends NetworkBlock {

	public BlastDoorBlock(Settings settings){
		super(settings);
	}

	@Override
	protected NetworkComponentBlockEntity createNetworkComponent(BlockPos pos, BlockState state) {
		return null;
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

}
