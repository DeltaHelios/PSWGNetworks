package dev.pswg;

import dev.pswg.api.GalaxiesAddon;
import dev.pswg.container.NetworksBlockEntities;
import dev.pswg.container.NetworksBlocks;
import dev.pswg.container.NetworksItemGroups;
import dev.pswg.container.NetworksItems;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

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

	@Override
	public void onGalaxiesReady() {
		NetworksItems.register();
		NetworksItemGroups.register();
		NetworksBlocks.register();
		NetworksBlockEntities.register();

		LOGGER.info("Module initialized");
	}

	// Need to figure out how to do this.
	/*@Override
	public void onInitialize(){
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			NetworkTable.CloseAll();
		});
	}*/
}
