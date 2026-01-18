package dev.pswg;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

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

		// Make sure there is a NetworkComponent at that location.
		BlockPos networkPos = context.getBlockPos();
		BlockEntity blockEntity = world.getBlockEntity(networkPos);
		if (!(blockEntity instanceof NetworkComponentBlockEntity networkComponent)) {
			return ActionResult.PASS;
		}

		// Make sure the Component has a node.
		NetworkNode node = networkComponent.node;
		if (node == null) {
			return ActionResult.PASS;
		}

		// Make sure the node is connected to a network.
		UUID networkId = node.GetNetworkId();
		if (networkId == null) {
			return ActionResult.PASS;
		}

		// if we pass all the checks, add the component.
		ItemStack stack = context.getStack();
		stack.set(Networks.UUID_COMPONENT_TYPE, networkId);
		return ActionResult.SUCCESS;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		super.appendTooltip(stack, context, displayComponent, textConsumer, type);
	}

	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		return super.getTooltipData(stack);
	}

	/*@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {

		if(X11.Screen.hasShiftDown()) {
			tooltip.add(Text.translatable("tooltip.pswg.networks.chisel.shift_down"));
		} else {
			tooltip.add(Text.translatable("tooltip.tutorialmod.chisel"));
		}

		if(stack.get(ModDataComponentTypes.COORDINATES) != null) {
			tooltip.add(Text.literal("Last Block Changed at " + stack.get(ModDataComponentTypes.COORDINATES)));
		}

		super.appendTooltip(stack, context, tooltip, type);
	}*/
}
