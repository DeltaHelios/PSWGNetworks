package dev.pswg.container;

import dev.pswg.Networks;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class NetworksSounds {
	private NetworksSounds(){

	}

	// ADD MORE SOUNDS HERE
	public static final SoundEvent TURRET_FIRE = registerSound("e11");

	private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.of(Networks.MODID, id);
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}

	public static void initialize() {
		Networks.LOGGER.info("Registering " + Networks.MODID + " Sounds");
	}
}
