package dev.pswg;

import dev.pswg.api.GalaxiesAddon;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

/**
 * The main entrypoint for PSWG common-side blaster features
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
	 * A logger available only to PSWG module and addon blasters
	 */
	public static final Logger LOGGER = Galaxies.createSubLogger(MODID);

	@Override
	public void onGalaxiesReady()
	{
		LOGGER.info("Module initialized");
	}
}
