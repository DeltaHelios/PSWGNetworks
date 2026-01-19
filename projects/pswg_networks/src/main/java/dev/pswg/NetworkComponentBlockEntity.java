package dev.pswg;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public abstract class NetworkComponentBlockEntity extends BlockEntity {

	@Nullable
	public NetworkNode node;

	protected void disposeNode(){
		if(node != null){
			node.close();
		}
		node = null;
	}


	public NetworkComponentBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (!(itemStack.getItem() instanceof NetworkComponentPlacerItem)){
			Networks.LOGGER.info("Attempted to pull network ID from ItemStack, but item is not a NetworkComponentPlacerItem");
			return;
		}

		node = NetworkNode.Create(new GlobalPos(world.getRegistryKey(), pos));

		@Nullable UUID networkId = itemStack.get(Networks.UUID_COMPONENT_TYPE);

		if(networkId == null){
			Networks.LOGGER.info("Attempted to assign Network ID, but got null from the item stack.");
			return;
		}

		node.SetNetwork(networkId);

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
