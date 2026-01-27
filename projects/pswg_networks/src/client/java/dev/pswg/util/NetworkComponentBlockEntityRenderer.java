package dev.pswg.util;

import dev.pswg.NetworkComponentBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import static dev.pswg.util.OutlineRenderUtil.drawVoxelShapeOutline;

@Environment(EnvType.CLIENT)
public class NetworkComponentBlockEntityRenderer implements BlockEntityRenderer<NetworkComponentBlockEntity, NetworkComponentBlockEntityRenderState> {
	public NetworkComponentBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){}

	@Override
	public NetworkComponentBlockEntityRenderState createRenderState() {
		return new NetworkComponentBlockEntityRenderState();
	}

	@Override
	public void render(
			NetworkComponentBlockEntityRenderState state,
			MatrixStack matrices,
			OrderedRenderCommandQueue queue,
			CameraRenderState cameraState
	) {
		matrices.push();

		queue.submitCustom(matrices, RenderLayer.getLines(), (matricesEntry, vertexConsumer) -> {
			if(state.glowing){
				if(state.shape == null){
					throw new IllegalStateException("VoxelShape is null for NetworkComponentBlockEntityRenderState!");
				}

				drawVoxelShapeOutline(
						matricesEntry,
						vertexConsumer,
						state.shape,
						0.2f,
						0.8f,
						1.0f,
						1.0f
				);
			}
		});

		matrices.pop();
	}

	@Override
	public void updateRenderState(
			NetworkComponentBlockEntity blockEntity,
			NetworkComponentBlockEntityRenderState state,
			float tickProgress,
			Vec3d cameraPos,
			@Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay
	){
		BlockEntityRenderState.updateBlockEntityRenderState(blockEntity, state, crumblingOverlay);

		state.shape = blockEntity.Shape;
		state.glowing = blockEntity.Glowing;
	}
}
