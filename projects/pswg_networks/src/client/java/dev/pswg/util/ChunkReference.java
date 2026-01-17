package dev.pswg.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ChunkReference {
	@NotNull
	private final ChunkPos _location;

	@NotNull
	private final RegistryKey<World> _world;

	public ChunkReference(@NotNull ChunkPos location, @NotNull RegistryKey<World> world){
		Objects.requireNonNull(location);
		Objects.requireNonNull(world);
		_location = location;
		_world = world;
	}

	public ChunkReference(@NotNull WorldChunk chunk){
		Objects.requireNonNull(chunk);
		_location = chunk.getPos();
		_world = chunk.getWorld().getRegistryKey();
	}

	/**
	 * Resolves this {@code ChunkReference} to a loaded {@link WorldChunk} on the server.
	 *
	 * <p>This method will <strong>not</strong> load or generate the chunk. It only
	 * returns a chunk if it is already loaded in memory.</p>
	 *
	 * @param server the {@link MinecraftServer} used to resolve the world
	 * @return the loaded {@link WorldChunk}, or {@code null} if the chunk is not currently loaded
	 * @throws IllegalStateException if the referenced world is not loaded on the server
	 */
	@Nullable
	public WorldChunk ToChunk(MinecraftServer server){

		ServerWorld serverWorld = server.getWorld(_world);

		if(serverWorld == null){
			throw new IllegalStateException("World is not loaded: " + _world.getValue());
		}

		WorldChunk chunk = serverWorld.getChunkManager().getWorldChunk(
				_location.x,
				_location.z
		);

		return chunk;
	}
}
