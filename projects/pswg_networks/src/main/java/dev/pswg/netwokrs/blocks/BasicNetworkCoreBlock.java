package dev.pswg.netwokrs.blocks;

import dev.pswg.Network;
import dev.pswg.NetworkComponent;
import dev.pswg.NetworkTable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class BasicNetworkCoreBlock extends NetworkComponent {


	public BasicNetworkCoreBlock(
			BlockEntityType<?> type,
			BlockPos pos,
			BlockState state,
			@Nullable UUID networkId) {
		super(type, pos, state);

		@Nullable Network target = null;


		if(networkId != null){
			target = Network.FromId(networkId);
		}

		if(target == null){
			assert world != null : "NetworkConstructor should have validated that world is not null.";

			target = Network.Create(world.getRegistryKey());
		}

		target.AddNode(node);
	}
}
