package dev.pswg.util;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.Objects;

public final class OutlineRenderUtil {
	/**
	 * @param entry includes data like, where is the camera and where is it facing.
	 * @param consumer Where the data will go
	 * @param shape the shape we are tracing.
	 * @param red the amount of red in the color
	 * @param green the amount of green in the color
	 * @param blue the amount of blue in the color
	 * @param alpha how transparent the color is.
	 */
	public static void drawVoxelShapeOutline(@NotNull MatrixStack.Entry entry, VertexConsumer consumer, VoxelShape shape, float red, float green, float blue, float alpha) {
		Matrix4f positionMatrix = Objects.requireNonNull(entry).getPositionMatrix();

		shape.forEachEdge((double x1, double y1, double z1, double x2, double y2, double z2) -> {
			consumer
				.vertex(positionMatrix, (float)x1, (float)y1, (float)z1)
				.color(red, green, blue, alpha);

			consumer
				.vertex(positionMatrix, (float)x2, (float)y2, (float)z2)
		        .color(red, green, blue, alpha);
		});
	}
}
