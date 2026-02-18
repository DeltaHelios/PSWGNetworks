package dev.pswg.blocks;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.serialization.MapCodec;
import dev.pswg.Galaxies;
import dev.pswg.NetworkBlock;
import dev.pswg.NetworkComponentBlockEntity;
import dev.pswg.Networks;
import dev.pswg.blockEntities.MiniTurretBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MiniTurretBlock extends BlockWithEntity implements BlockEntityProvider{

	public static final MapCodec<MiniTurretBlock> CODEC = MiniTurretBlock.createCodec(MiniTurretBlock::new);

	public MiniTurretBlock(Settings settings) {
		super(settings);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new MiniTurretBlockEntity(pos, state);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if(!world.isClient()) {

		}

		return ActionResult.SUCCESS;

	}

}
