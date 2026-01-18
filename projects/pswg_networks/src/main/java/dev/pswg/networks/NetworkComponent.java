package dev.pswg.networks;

import dev.pswg.Network;
import dev.pswg.NetworkNode;
import dev.pswg.Networks;
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

public abstract class NetworkComponent extends BlockEntity {

	@Nullable
	public NetworkNode node;

	protected void disposeNode(){
		if(node != null){
			node.close();
		}
		node = null;
	}


	public NetworkComponent(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		Objects.requireNonNull(world);
		Objects.requireNonNull(itemStack);

		if (!(itemStack.getItem() instanceof NetworkComponentPlacer)){
			// TODO: no idea what to do here.
		}

		@Nullable UUID networkId = itemStack.get(Networks.UUID_COMPONENT_TYPE);

		if(networkId != null){
			node = NetworkNode.Create(new GlobalPos(world.getRegistryKey(), pos));

			Network.FromId(networkId);
		}

		// attempt to connect to the network.
	}

	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		disposeNode();
		// disconnect from the network
	}

	public void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		if(moved){
			// disconnect from the network, attempt to reconnect based on new blockPos.
			// on a failure to reconnect, dispose of node.
		}
	}
}
