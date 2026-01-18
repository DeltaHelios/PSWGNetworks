package dev.pswg.blockEntities;

import dev.pswg.Network;
import dev.pswg.NetworkComponentBlockEntity;
import dev.pswg.Networks;
import dev.pswg.container.NetworksBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public final class BasicNetworkCoreBlockEntity extends NetworkComponentBlockEntity {

	public BasicNetworkCoreBlockEntity(BlockPos pos, BlockState state) {
		super(NetworksBlockEntities.BASIC_NETWORK_CORE_BLOCK_ENTITY, pos, state);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		Objects.requireNonNull(itemStack);
		Objects.requireNonNull(world);

		// TODO: validate that itemStack item type inherits from NetworkComponentPlacer

		@Nullable UUID networkId = itemStack.get(Networks.UUID_COMPONENT_TYPE);

		if(networkId == null){
			;
		}
		itemStack.set(Networks.UUID_COMPONENT_TYPE, Network.Create(world.getRegistryKey()).getId());
		super.onPlaced(world, pos, state, placer, itemStack);
	}
}
