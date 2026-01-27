package dev.pswg.util;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NetworkComponentBlockEntityRenderState extends BlockEntityRenderState {
	public boolean glowing = false;

	@Nullable
	public VoxelShape shape;
}
