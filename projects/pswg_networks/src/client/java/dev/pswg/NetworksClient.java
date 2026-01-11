package dev.pswg;

import dev.pswg.api.GalaxiesClientAddon;

/**
 * The main entrypoint for PSWG client-side blaster features
 */
public class NetworksClient implements GalaxiesClientAddon
{

	@Override
	public void onGalaxiesClientReady()
	{
		Networks.LOGGER.info("Client module initialized");
	}

}
