package dev.pswg.blockEntities;

import dev.pswg.container.NetworksBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class MiniTurretBlockEntity extends BlockEntity {
	public MiniTurretBlockEntity(BlockPos pos, BlockState state) {
		super(NetworksBlockEntities.MINI_TURRET_BLOCK_ENTITY, pos, state);
	}


}
