package dev.pswg.util;

import dev.pswg.NetworkComponentBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

public class NetworkComponentBlockEntityRendererFactory implements BlockEntityRendererFactory<NetworkComponentBlockEntity, NetworkComponentBlockEntityRenderState> {
	@Override
	public BlockEntityRenderer<NetworkComponentBlockEntity, NetworkComponentBlockEntityRenderState> create(Context ctx) {
		return new NetworkComponentBlockEntityRenderer(ctx);
	}
}
