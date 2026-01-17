package dev.pswg.netwokrs.items;

import dev.pswg.container.NetworksBlocks;
import dev.pswg.netwokrs.NetworkComponentPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BasicNetworkCoreItem extends NetworkComponentPlacer {

	public BasicNetworkCoreItem(Settings settings) {
		super(NetworksBlocks.BASIC_NETWORK_CORE, settings);
	}
}
