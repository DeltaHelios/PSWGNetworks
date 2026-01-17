package dev.pswg.netwokrs.blocks;

import dev.pswg.Network;
import dev.pswg.NetworkComponent;
import dev.pswg.NetworkTable;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class BasicNetworkCoreBlockEntity extends NetworkComponent {


	public BasicNetworkCoreBlockEntity(
			BlockEntityType<?> type,
			BlockPos pos,
			BlockState state,
			@Nullable UUID networkId) {
		super(type, pos, state);

		@Nullable Network target = null;

		if(networkId == null){
			assert world != null : "NetworkConstructor should have validated that world is not null.";

			target = Network.Create(world.getRegistryKey());
		}
		else{
			Map<@NotNull UUID, @NotNull Network> networks = NetworkTable.GetNetworks();

			if(!networks.containsKey(networkId)){
				// GPT: throw something.
			}

			target = networks.get(networkId);
		}

		target.AddNode(node);
	}
}
