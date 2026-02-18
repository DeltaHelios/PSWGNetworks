package dev.pswg.renderers;

import dev.pswg.Networks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.data.Model;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class TurretBlockEntityRenderer implements BlockEntityRenderer<BlockEntity, BlockEntityRenderState> {

	public static final Identifier TEXTURE = (Networks.id("textures/block/mini_turret.png"));


	@Override
	public BlockEntityRenderState createRenderState() {

		return null;
	}




	@Override
	public void render(BlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {

	}




//	@Override
//	public boolean isInRenderDistance(MiniTurretBlock blockEntity, Vec3d pos) {
//		return BlockEntityRenderer.super.isInRenderDistance(blockEntity, pos);
//	}
}
