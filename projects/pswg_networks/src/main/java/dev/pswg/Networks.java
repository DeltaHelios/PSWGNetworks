package dev.pswg;

import com.mojang.serialization.Codec;
import dev.pswg.api.GalaxiesAddon;
import dev.pswg.container.NetworksBlockEntities;
import dev.pswg.container.NetworksBlocks;
import dev.pswg.container.NetworksItemGroups;
import dev.pswg.container.NetworksItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.function.UnaryOperator;

/**
 * The main entrypoint for PSWG common-side network features
 */
public final class Networks implements GalaxiesAddon
{
	/**
	 * The mod ID assigned to PSWG
	 */
	public static final String MODID = "pswg_networks";

	/**
	 * Creates a scoped {@link Identifier} whose domain is this
	 * mod's MODID
	 *
	 * @param path The path for the {@link Identifier}
	 *
	 * @return A scoped {@link Identifier}
	 */
	public static Identifier id(String path)
	{
		return Identifier.of(MODID, path);
	}

	/**
	 * A logger available only to PSWG module and addon networks
	 */
	public static final Logger LOGGER = Galaxies.createSubLogger(MODID);

	private static <T> ComponentType<T> registerDataComponent(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, name),
		                         builderOperator.apply(ComponentType.builder()).build());
	}

	public static final ComponentType<UUID> UUID_COMPONENT_TYPE = registerDataComponent(
			"uuid", builder -> builder.codec(Uuids.CODEC)
	);

	public static final Codec<RegistryKey<World>> WORLD_KEY_CODEC =
			RegistryKey.createCodec(RegistryKeys.WORLD);

	@Override
	public void onGalaxiesReady() {
		NetworksItems.register();
		NetworksItemGroups.register();
		NetworksBlocks.register();
		NetworksBlockEntities.register();

		// When the server stops dispose of all networks (which will also dispose all nodes).
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			NetworkTable.CloseAll();
		});

		LOGGER.info("Module initialized");
	}
}
