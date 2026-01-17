package dev.pswg.util;

import dev.pswg.NetworkNode;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public final class NetworkDefaults {

	// This is effectively a static class. We should never create an instance of it.
	private NetworkDefaults() {}

	public record RangeCacheKey(BlockPos center, int range) { }

	// This could be converted to offsets. Might be a good idea. I don't wanna.
	public static final ConcurrentHashMap<RangeCacheKey, Set<BlockPos>> RangeCache = new ConcurrentHashMap<>();

	@SuppressWarnings("StaticNonFinalField") // disabled because we want this value to be data driven.
	public static volatile BiFunction<@NotNull Integer, @NotNull NetworkNode, @NotNull Set<GlobalPos>> DefaultRangeFinder = (range, self) -> {
		Objects.requireNonNull(range, "range");
		Objects.requireNonNull(self, "self");

		if (range < 0){
			throw new IllegalArgumentException("range must be >= 0");
		}

		BlockPos center = self.GetBlockPos();
		RangeCacheKey targetKey = new RangeCacheKey(center, range);

		Set<BlockPos> basicRangeSet = NetworkDefaults.RangeCache.computeIfAbsent(targetKey, (RangeCacheKey ignored) -> {
			int centerX = ignored.center.getX();
			int centerY = ignored.center.getY();
			int centerZ = ignored.center.getZ();

			long rangeSquared = Math.multiplyExact(ignored.range, (long)ignored.range);

			int diameter = Math.multiplyExact(ignored.range, 2) + 1;
			int expectedSizeUpperBound = Math.multiplyExact(diameter, Math.multiplyExact(diameter, diameter));

			HashSet<BlockPos> blockPositions = new HashSet<>(expectedSizeUpperBound);

			// I think this is *probably* faster than Math.sqrt().

			// Iterate over all X offsets within the radius around the center
			for (int dx = -ignored.range; dx <= ignored.range; dx++){
				// Square of the X offset (used for distance calculation)
				int dx2 = dx * dx;

				// Iterate over all Y offsets within the radius around the center
				for (int dy = -ignored.range; dy <= ignored.range; dy++){
					// Squared distance in the X–Y plane from the center
					int dxy2 = dx2 + (dy * dy);

					// If the point is already outside the spherical radius in X–Y,
					// skip checking Z offsets entirely
					if (dxy2 <= rangeSquared){

						// Iterate over all Z offsets within the radius around the center
						for (int dz = -ignored.range; dz <= ignored.range; dz++){
							// Full squared 3D distance from the center (X, Y, Z)
							int dist2 = dxy2 + (dz * dz);

							// Include this position only if it lies within the sphere
							if (dist2 <= rangeSquared){
								// Add the absolute block position relative to the center
								blockPositions.add(new BlockPos(centerX + dx, centerY + dy, centerZ + dz));
							}
						}
					}
				}
			}


			return Set.copyOf(blockPositions);
		});

		RegistryKey<World> dimension = self.GetDimension();
		HashSet<GlobalPos> globalPositions = new HashSet<>(basicRangeSet.size());
		for (BlockPos pos : basicRangeSet){
			globalPositions.add(GlobalPos.create(dimension, pos));
		}

		return Set.copyOf(globalPositions);
	};

}
