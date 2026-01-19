package dev.pswg.blocks;

import com.mojang.serialization.MapCodec;
import dev.pswg.NetworkBlock;
import dev.pswg.NetworkComponentBlockEntity;
import dev.pswg.blockEntities.BasicNetworkComponentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BasicNetworkComponentBlock extends NetworkBlock {
	public static final String NAME = "basic_network_componant";

	public BasicNetworkComponentBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	protected NetworkComponentBlockEntity createNetworkComponent(BlockPos pos, BlockState state) {
		return new BasicNetworkComponentBlockEntity(pos, state);
	}
}
