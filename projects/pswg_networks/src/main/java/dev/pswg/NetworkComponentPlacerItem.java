package dev.pswg;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class NetworkComponentPlacerItem extends BlockItem {

	public NetworkComponentPlacerItem(Block block, Settings settings) {
		super(block, settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		Objects.requireNonNull(context);
		World world = context.getWorld();

		// If the client is acting we don't need to do anything.
		if (world.isClient()) {
			return ActionResult.SUCCESS;
		}

		// make sure it's a player that's acting.
		PlayerEntity player = context.getPlayer();
		if (player == null) { // We may want to do away with this check to allow compatibility with things like Create's deployer.
							  // I don't know how that works though.
			return ActionResult.FAIL;
		}

		// make sure the player is seeking.
		if (!player.isSneaking()) {
			return super.useOnBlock(context);
		}

		@Nullable UUID networkId = null;

		// Make sure there is a NetworkComponent at that    location, otherwise we just clear the data from the item.
		BlockPos networkPos = context.getBlockPos();
		BlockEntity blockEntity = world.getBlockEntity(networkPos);
		if ((blockEntity instanceof NetworkComponentBlockEntity networkComponent)) {
			// Make sure the Component has a node.
			NetworkNode node = networkComponent.Node;
			if (node != null) {
				// Make sure the node is connected to a network.
				networkId = node.GetNetworkId();
			}
		}

		// if we pass all the checks, add the component.
		ItemStack stack = context.getStack();
		stack.set(Networks.UUID_COMPONENT_TYPE, networkId);
		return ActionResult.SUCCESS;
	}

	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		return super.getTooltipData(stack);
	}
}
