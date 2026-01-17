package dev.pswg.netwokrs.blocks;

import com.mojang.serialization.MapCodec;
import dev.pswg.Networks;
import dev.pswg.netwokrs.NetworkBlock;
import dev.pswg.netwokrs.NetworkComponent;
import dev.pswg.netwokrs.blockEntities.BasicNetworkCoreBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BasicNetworkCoreBlock extends NetworkBlock {

	public static final Identifier ID = Networks.id("basic_network_core");

	public BasicNetworkCoreBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	protected NetworkComponent createNetworkComponent(BlockPos pos, BlockState state) {
		return new BasicNetworkCoreBlockEntity(pos, state);
	}

	/*public BasicNetworkCoreBlock(
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
	}*/
}
