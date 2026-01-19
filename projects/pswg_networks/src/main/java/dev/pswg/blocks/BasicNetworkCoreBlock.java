package dev.pswg.blocks;

import com.mojang.serialization.MapCodec;
import dev.pswg.NetworkBlock;
import dev.pswg.NetworkComponentBlockEntity;
import dev.pswg.blockEntities.BasicNetworkCoreBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.util.math.BlockPos;

public class BasicNetworkCoreBlock extends NetworkBlock {

	public static final String NAME = "basic_network_core";

	public BasicNetworkCoreBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	protected NetworkComponentBlockEntity createNetworkComponent(BlockPos pos, BlockState state) {
		return new BasicNetworkCoreBlockEntity(pos, state);
	}
}
