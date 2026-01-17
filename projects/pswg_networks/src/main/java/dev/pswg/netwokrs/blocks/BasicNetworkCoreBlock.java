package dev.pswg.netwokrs.blocks;

import com.mojang.serialization.MapCodec;
import dev.pswg.Networks;
import dev.pswg.netwokrs.blockEntities.BasicNetworkCoreBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BasicNetworkCoreBlock extends BlockWithEntity {

	public static Identifier ID = Networks.id("basic_network_core");

	private UUID networkId;

	public BasicNetworkCoreBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BasicNetworkCoreBlockEntity(, pos, state);
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
