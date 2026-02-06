package dev.pswg.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.pswg.NetworkComponentBlockEntity;
import dev.pswg.Networks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
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

	public static final RenderLayer GLOW = RenderLayer.MultiPhase.of(
			Networks.MODID + ":glow",
			2097152,
			false, // blocks texture changes as it breaks?
			true, // I think this is the part that lets you see it though walls.
			RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
				.build(),
			RenderLayer.MultiPhaseParameters
				.builder()
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
				.texture(RenderPhase.Texture.NO_TEXTURE)
                .build(true)
	);

	@Override
	public void render(
			NetworkComponentBlockEntityRenderState renderState,
			MatrixStack matrices,
			OrderedRenderCommandQueue queue,
			CameraRenderState cameraState
	) {
		matrices.push();

		queue.submitCustom(matrices, GLOW, (matricesEntry, vertexConsumer) -> {
			if(renderState.glowing){
				if(renderState.shape == null){
					throw new IllegalStateException("VoxelShape is null for NetworkComponentBlockEntityRenderState!");
				}

				drawVoxelShapeOutline(
						matricesEntry,
						vertexConsumer,
						renderState.shape,
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
