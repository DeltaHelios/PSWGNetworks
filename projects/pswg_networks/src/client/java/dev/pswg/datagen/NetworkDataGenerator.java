package dev.pswg.datagen;

import dev.pswg.Galaxies;
import dev.pswg.Networks;
import dev.pswg.autoreg.AutoGenerateUtil;
import dev.pswg.container.NetworksBlocks;
import dev.pswg.container.NetworksItemGroups;
import dev.pswg.container.NetworksItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;

import java.util.*;
import java.util.concurrent.CompletableFuture;



/**
 * The network data generator
 */
public class NetworkDataGenerator implements DataGeneratorEntrypoint
{
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator)
	{
		var pack = generator.createPack();

		Galaxies.LOGGER.info("Running Networks Data Generator");

		pack.addProvider(LangGenerator::new);

		// rename to itemTagGenerator?
		pack.addProvider(TagGenerator::new);
		pack.addProvider(ModelGenerator::new);
		//pack.addProvider(BlockTagGenerator::new);
		//pack.addProvider(RecipesGenerator::new);


	}

	/**
	 * The network model generator. All models should be added through
	 * this generator.
	 */
	private static class ModelGenerator extends GalaxiesModelProvider
	{
		public ModelGenerator(FabricDataOutput output)
		{
			super(output);
		}

		public static WeightedVariant createWeighedVarient(Identifier id)
		{
			return new WeightedVariant(Pool.of(new ModelVariant(id)));
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
		{
			AutoGenerateUtil.consumeAnnotatedFields(DataGenBlock.class, NetworksBlocks.class, Block.class, (genBlock, dataGenBlock) -> {
				registerDataGenBlock(genBlock, dataGenBlock, blockStateModelGenerator);
			});
		}

		private static void registerDataGenBlock(Block block, DataGenBlock dataGenBlock, BlockStateModelGenerator generator)
		{
			switch (dataGenBlock.model())
			{
				case CubeAll -> registerCubeWithRotation(block, dataGenBlock, TexturedModel.CUBE_ALL, generator);
				case Custom ->
				{
					switch (dataGenBlock.dataGenModelKey())
					{
						case null, default:
					}
				}
			}
		}

		private static void registerCubeWithRotation(Block block, DataGenBlock dataGenBlock, TexturedModel.Factory modelFactory, BlockStateModelGenerator generator) {
			switch (dataGenBlock.rotation()){
				case Default -> generator.registerSingleton(block, modelFactory);
			}
		}

		private static Model blockModel(String parent, TextureKey... requiredTextureKeys)
		{
			return new Model(Optional.of(Networks.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		private static Identifier getBlockKey(Block block)
		{
			return block.getRegistryEntry().getKey().get().getValue();
		}

		public static Identifier createItemKey(Item item, DataGenItem dataGenItem)
		{
			if (dataGenItem.textureOverride().equals(""))
				return item.getRegistryEntry().getKey().get().getValue().withPrefixedPath("item/");
			return Networks.id(dataGenItem.textureOverride()).withPrefixedPath("item/");
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator)
		{
			AutoGenerateUtil.consumeAnnotatedFields(DataGenItem.class, NetworksItems.class, Item.class, (item, dataGenItem) -> registerItem(itemModelGenerator, item, dataGenItem));
		}

		public void registerItem(ItemModelGenerator generator, Item item, DataGenItem dataGenItem)
		{
			if (dataGenItem.genModel())
				register(generator, item, Galaxies.id("item/wizard"), Models.GENERATED);
			else
				switch (dataGenItem.model())
				{
					case generated -> register(generator, item, createItemKey(item, dataGenItem), Models.GENERATED);
					case handheld -> register(generator, item, createItemKey(item, dataGenItem), Models.HANDHELD);
				}
		}
	}

	/**
	 * The network language file generator. All language entries should be
	 * added through this generator.
	 */
	private static class LangGenerator extends FabricLanguageProvider
	{
		protected LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup)
		{
			super(dataOutput, "en_us", registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder)
		{
			translationBuilder.add(NetworksItemGroups.NETWORKS_GROUP_KEY, "PSWG - Networks");
		}
	}
	/**
	 * The network item tag generator. All item tags should be added
	 * through this generator.
	 */
	private static class TagGenerator extends FabricTagProvider.ItemTagProvider
	{
		public TagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
		{
			super(output, completableFuture);
		}

		public static Identifier itemId(Item item)
		{
			return Registries.ITEM.getId(item);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup)
		{

		}
	}
}
