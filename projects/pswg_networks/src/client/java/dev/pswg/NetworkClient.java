package dev.pswg;

import dev.pswg.api.GalaxiesClientAddon;
import dev.pswg.data.*;

/**
 * The main entrypoint for PSWG client-side blaster features
 */
public class NetworkClient implements GalaxiesClientAddon
{

	@Override
	public void onGalaxiesClientReady()
	{

		Networks.LOGGER.info("Client module initialized");
	}

}
