package dev.pswg;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class NetworkComponentBlockEntity extends BlockEntity {

	@Nullable
	public NetworkNode Node;

	@Nullable
	public VoxelShape Shape;

	public boolean Glowing = false;

	/**
	 * Constructs a {@code NetworkComponentBlockEntity} using the outline shape
	 * of the attached block as its base shape.
	 *
	 * @param type the block entity type
	 * @param pos  the block position
	 * @param state the block state at {@code pos}
	 */
	public NetworkComponentBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos pos, @NotNull BlockState state) {
		super(type, pos, state);
		Shape = getBaseShape();
	}

	/**
	 * Constructs a {@code NetworkComponentBlockEntity} with an explicit base
	 * {@link VoxelShape}.
	 *
	 * @param type  the block entity type
	 * @param pos   the block position
	 * @param state the block state at {@code pos}
	 * @param shape the base {@link VoxelShape} to associate with this block entity
	 *
	 * @throws NullPointerException if {@code shape} is {@code null}
	 */
	public NetworkComponentBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull VoxelShape shape) {
		super(type, pos, state);
		Shape = Objects.requireNonNull(shape);
	}

	protected void disposeNode(){
		if(Node != null){
			NetworkTable.NodeToComponentPackagePrivate.remove(Node);
			Node.close();
		}
		Node = null;
	}

	/**
	 * Returns the base {@link VoxelShape} for outline shape of the attached block.
	 *
	 * <p><strong>Warning:</strong> If {@link #world} is {@code null}, this method
	 * will call {@link AbstractBlock#getOutlineShape(BlockState, BlockView, BlockPos, ShapeContext)} with a {@code null} world reference.
	 * Blocks whose outline shape implementation assumes a non-null world
	 * ({@link ShulkerBoxBlock} in vanilla) may throw a
	 * {@link NullPointerException} in this case.</p>
	 *
	 * @return the outline {@link VoxelShape} of the attached block
	 */
	private VoxelShape getBaseShape() {
		BlockState blockState = getCachedState();

		// WARNING: world is nullable. if its null and if this is attached to a block that requires world for its implementation of AbstractBlock.getOutlineShape()
		// this will throw a null pointer exception.
		// In normal minecraft that's only the ShulkerBoxBlock that I could find.
		return blockState.getOutlineShape(world, pos, ShapeContext.absent());
	}



	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (!(itemStack.getItem() instanceof NetworkComponentPlacerItem)){
			Networks.LOGGER.info("Attempted to pull network ID from ItemStack, but item is not a NetworkComponentPlacerItem");
			return;
		}

		Node = NetworkNode.Create(new GlobalPos(world.getRegistryKey(), pos));
		NetworkTable.NodeToComponentPackagePrivate.put(Node, Optional.of(this));

		@Nullable UUID networkId = itemStack.get(Networks.UUID_COMPONENT_TYPE);

		if(networkId == null){
			Networks.LOGGER.info("Attempted to assign Network ID, but got null from the item stack.");
			return;
		}

		Objects.requireNonNull(Network.FromId(networkId)).AddNode(Node);

		// attempt to connect to the network.
	}

	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		disposeNode();
		// disconnect from the network
	}

	public void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		if(moved){
			//TODO: need to set this up.
			//      If moved is true we need to attempt to reconnect from our new position.
		}
	}
}
