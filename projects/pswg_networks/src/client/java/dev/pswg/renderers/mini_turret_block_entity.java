package dev.pswg.renderers;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class mini_turret_block_entity extends EntityModel<EntityRenderState> {
	private final ModelPart turret;
	private final ModelPart head;
	private final ModelPart barrel;


//	private final ModelPart base;
//	private final ModelPart neck;
//	private final ModelPart head;
//	private final ModelPart rails;
//	private final ModelPart hexadecagon2;
//	private final ModelPart hexadecagon;
//	private final ModelPart barrel;
//	private final ModelPart bone;

	public mini_turret_block_entity(ModelPart root) {
		super(root);
		this.turret = root.getChild("turret");
		this.head = turret.getChild("base").getChild("neck").getChild("head");
		this.barrel = turret.getChild("base").getChild("neck").getChild("head").getChild("barrel");
//
//		this.base = root.getChild("base");
//		this.neck = this.base.getChild("neck");
//		this.head = this.neck.getChild("head");
//		this.rails = this.head.getChild("rails");
//		this.hexadecagon2 = this.rails.getChild("hexadecagon2");
//		this.hexadecagon = this.rails.getChild("hexadecagon");
//		this.barrel = this.head.getChild("barrel");
//		this.bone = root.getChild("bone");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -16.0F, 0.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, 24.0F, -8.0F,0f, 0f, 0f));

		ModelPartData neck = base.addChild("neck", ModelPartBuilder.create().uv(37, 48).cuboid(1.0F, -17.0F, 3.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F,0f, 0f, 0f));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(65, 0).cuboid(-7.0F, -14.0F, -7.0F, 2.0F, 10.0F, 10.0F, new Dilation(0.0F))
		.uv(65, 21).cuboid(5.0F, -14.0F, -7.0F, 2.0F, 10.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 33).cuboid(-7.0F, -4.0F, -7.0F, 14.0F, 4.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 48).cuboid(-7.0F, -14.0F, 3.0F, 14.0F, 14.0F, 4.0F, new Dilation(0.0F))
		.uv(37, 60).cuboid(-2.0F, -14.0F, -7.0F, 4.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -17.0F, 8.0F,0f, 0f, 0f));

		ModelPartData rails = head.addChild("rails", ModelPartBuilder.create(), ModelTransform.of(-6.0F, 0.0F, -8.0F, 0f, 0f, 0f));

		ModelPartData hexadecagon2 = rails.addChild("hexadecagon2", ModelPartBuilder.create().uv(30, 83).cuboid(-8.0F, -9.5913F, 0.0F, 3.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(23, 107).cuboid(-8.0F, -16.0F, 6.4087F, 3.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(16.0F, 1.0F, 0.0F,0f, 0f, 0f));

		ModelPartData hexadecagon_r1 = hexadecagon2.addChild("hexadecagon_r1", ModelPartBuilder.create().uv(20, 90).cuboid(0.0F, -3.1913F, 0.0F, 3.0F, 3.1826F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -14.7882F, 3.4745F, -1.1781F, 0.0F, 0.0F));

		ModelPartData hexadecagon_r2 = hexadecagon2.addChild("hexadecagon_r2", ModelPartBuilder.create().uv(20, 95).cuboid(0.0F, -1.5913F, -8.0F, 3.0F, 3.1826F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData hexadecagon_r3 = hexadecagon2.addChild("hexadecagon_r3", ModelPartBuilder.create().uv(7, 123).cuboid(0.0F, -1.5913F, -8.0F, 3.0F, 3.1826F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, -0.3927F, 0.0F, 0.0F));

		ModelPartData hexadecagon = rails.addChild("hexadecagon", ModelPartBuilder.create().uv(34, 92).cuboid(-8.0F, -9.5913F, 0.0F, 3.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(3, 86).cuboid(-8.0F, -16.0F, 6.4087F, 3.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, 1.0F, 0.0F,0f, 0f, 0f));

		ModelPartData hexadecagon_r4 = hexadecagon.addChild("hexadecagon_r4", ModelPartBuilder.create().uv(8, 103).cuboid(0.0F, -3.1913F, 0.0F, 3.0F, 3.1826F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -14.7882F, 3.4745F, -1.1781F, 0.0F, 0.0F));

		ModelPartData hexadecagon_r5 = hexadecagon.addChild("hexadecagon_r5", ModelPartBuilder.create().uv(8, 108).cuboid(0.0F, -1.5913F, -8.0F, 3.0F, 3.1826F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData hexadecagon_r6 = hexadecagon.addChild("hexadecagon_r6", ModelPartBuilder.create().uv(25, 116).cuboid(0.0F, -1.5913F, -8.0F, 3.0F, 3.1826F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, -0.3927F, 0.0F, 0.0F));

		ModelPartData barrel = head.addChild("barrel", ModelPartBuilder.create().uv(49, 33).cuboid(-6.0F, -2.0F, -11.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(49, 40).cuboid(2.0F, -2.0F, -11.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(78, 49).cuboid(3.0F, -1.0F, -9.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(66, 79).cuboid(-4.0F, -1.0F, -9.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(17, 67).cuboid(2.0F, -1.0F, -15.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(66, 67).cuboid(2.5F, -0.5F, -20.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 74).cuboid(-3.5F, -0.5F, -20.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F))
		.uv(13, 74).cuboid(-4.0F, -1.0F, -15.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F,0f, 0f, 0f));

		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 81).cuboid(-9.0F, -25.0F, 7.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 24.0F, -8.0F,0f, 0f, 0f));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(EntityRenderState state) {

	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
	}

	public ModelPart getPart() {
		return turret;
	}


}