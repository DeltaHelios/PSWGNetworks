package dev.pswg.netwokrs;

import dev.pswg.netwokrs.blockEntities.BasicNetworkCoreBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public abstract class NetworkBlock extends BlockWithEntity {

	protected NetworkBlock(Settings settings) {
		super(settings);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return createNetworkComponent(pos, state);
	}

	protected abstract NetworkComponent createNetworkComponent(BlockPos pos, BlockState state);

	@Override
	protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		super.onStateReplaced(state, world, pos, moved);

		@Nullable BlockEntity entity = world.getBlockEntity(pos);

		if(entity == null){
			return;
		}

		if (entity instanceof NetworkComponent component) {
			component.onStateReplaced(state, world, pos, moved);
			return;
		}
		throw new IllegalStateException(
				"Expected BlockEntity implementing NetworkComponent at " + pos + " but got " + entity.getType().toString()
		);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);

		@Nullable BlockEntity entity = world.getBlockEntity(pos);

		if(entity == null){
			return;
		}

		if (entity instanceof NetworkComponent component) {
			component.onPlaced(world, pos, state, placer, itemStack);
			return;
		}
		throw new IllegalStateException(
				"Expected BlockEntity implementing NetworkComponent at " + pos + " but got " + entity.getType().toString()
		);
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		super.onBroken(world, pos, state);

		@Nullable BlockEntity entity = world.getBlockEntity(pos);

		if(entity == null){
			return;
		}

		if (entity instanceof NetworkComponent component) {
			component.onBroken(world, pos, state);
			return;
		}
		throw new IllegalStateException(
				"Expected BlockEntity implementing NetworkComponent at " + pos + " but got " + entity.getType().toString()
		);
	}
}
