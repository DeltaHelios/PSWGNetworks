package dev.pswg.blocks;

import com.mojang.serialization.MapCodec;
import dev.pswg.NetworkBlock;
import dev.pswg.NetworkComponentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.function.Function;

public class DataTerminalBlock extends NetworkBlock {
	public DataTerminalBlock(Settings settings) {
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
