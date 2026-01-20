package dev.pswg;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.pswg.util.NetworkDefaults;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.jcip.annotations.GuardedBy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;

public class NetworkNode implements AutoCloseable {
	@NotNull
	private final UUID idPrivate;

	@SuppressWarnings("FieldCanBeLocal")
	private final int rangePrivate;

	@NotNull
	private final GlobalPos locationPrivate;

	/**
	 * The unique identifier of the network this node is a part of.
	 *
	 * <p>This value is {@code null} until the object is assigned to a network.</p>
	 */
	@Nullable
	@GuardedBy("Network._lock")
	private UUID networkIdPrivate = null;

	@NotNull
	private final ReadWriteLock lockPrivate = new ReentrantReadWriteLock();

	/**
	 * Returns the identifier of the network this object belongs to.
	 * <b color=Red>Thread-safety:</b> The caller <em>must</em> hold the owning {@link Network}'s
	 * write lock when invoking this method. This requirement applies to <em>all</em>
	 * callers.
	 *
	 * @return the network UUID, or {@code null} if this object is not assigned to any network
	 */
	@Nullable
	public UUID GetNetworkId(){
		lockPrivate.readLock().lock();
		try{
			return networkIdPrivate;
		}
		finally {
			lockPrivate.readLock().unlock();
		}
	}

	/**
	 * Assigns this object to a network if it is not already associated with one.
	 *
	 * <p>This method will only succeed once. If a network ID is already set,
	 * the assignment is rejected and the method returns {@code false}.</p>
	 *
	 * <p><b color=Red>Thread-safety:</b> The caller <em>must</em> hold the owning {@link Network}'s
	 * write lock when invoking this method.</p>
	 *
	 * This method should only be called by {@link Network}.
	 *
	 * @param NetworkID the UUID of the network to assign
	 * @return {@code true} if the network ID was set successfully;
	 *         {@code false} if this object was already assigned to a network
	 */
	boolean SetNetwork(UUID NetworkID){
		Objects.requireNonNull(NetworkID, "NetworkID");
		lockPrivate.writeLock().lock();
		try{
			if(networkIdPrivate == null){
				networkIdPrivate = NetworkID;
				return true;
			}
			else{
				return false;
			}
		}
		finally {
			lockPrivate.writeLock().unlock();
		}
	}

	@NotNull
	public RegistryKey<World> GetDimension(){
		return locationPrivate.dimension();
	}

	@NotNull
	public BlockPos GetBlockPos(){
		return locationPrivate.pos();
	}

	@NotNull
	public GlobalPos GetPos(){
		return locationPrivate;
	}

	public static @Nullable NetworkNode FromId(@NotNull UUID id){
		Objects.requireNonNull(id);
		return NetworkTable.NodesPackagePrivate.get(id);
	}


	public static @NotNull NetworkNode Create(@NotNull GlobalPos location){
		NetworkNode output = new NetworkNode(location, 10);
		NetworkTable.NodesPackagePrivate.put(output.idPrivate, output);
		return output;
	}

	private NetworkNode(@NotNull GlobalPos location, int range){
		Objects.requireNonNull(location);
		locationPrivate = location;
		idPrivate = UUID.randomUUID();
		rangePrivate = range;
	}

	private NetworkNode(@NotNull UUID id, @NotNull GlobalPos location, int range) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(location);
		idPrivate = id;
		locationPrivate = location;
		rangePrivate = range;
	}

	private final BiFunction<@NotNull Integer, @NotNull NetworkNode, @NotNull Set<GlobalPos>> rangeFinderPrivate = NetworkDefaults.DefaultRangeFinder;

	public Set<GlobalPos> GetRange(){
		return rangeFinderPrivate.apply(rangePrivate, this);
	}
	@Override
	public void close() {
		NetworkTable.NodesPackagePrivate.remove(idPrivate);
	}

	// The below 4 fields are just for codecs.
	// I don't know where else I will need them, but it's better to have them be configurable.
	private static final String idPrivateKey = "id";

	private static final String locationPrivateKey = "location";

	private static final String rangePrivateKey = "range";

	private static final String networkIdPrivateKey = "network_id";

	/**
	 * Codec used to serialize and deserialize {@link NetworkNode} to NBT.
	 */
	@NotNull
	public static final Codec<NetworkNode> CODEC = RecordCodecBuilder.create(instance -> instance
		.group(
			// Define which fields we need to save and the Codecs for saving them
			Uuids.CODEC.fieldOf(idPrivateKey).forGetter(nn -> nn.idPrivate),
			GlobalPos.CODEC.fieldOf(locationPrivateKey).forGetter(x -> x.locationPrivate),
			Codec.INT.fieldOf(rangePrivateKey).forGetter(nn -> nn.rangePrivate),
			Uuids.CODEC.optionalFieldOf(networkIdPrivateKey).forGetter(nn -> Optional.ofNullable(nn.GetNetworkId()))
		// Define how we get an instance of NetworkNode back from the fields we defined above
		).apply(instance, (id, location, range, networkIdOpt) -> {
			NetworkNode node = new NetworkNode(id, location, range);
			// if networkIdOpt is not null,      run this lambda
			networkIdOpt.ifPresent(networkId -> node.networkIdPrivate = networkId);
			return node;
		})
	);

	/**
	 * Generates a {@link NbtCompound} from a {@link NetworkNode} object.
	 * <p>
	 * Used for long term storage.
	 * </p>
	 *
	 * @return the {@link NetworkNode} as an {@link NbtCompound}
	 */
	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound();

		CODEC
			// Use the codec to encode to NBT.
			.encodeStart(NbtOps.INSTANCE, this)
			// if the results don't look right, throw an error.
		    .resultOrPartial(msg -> {
				throw new IllegalStateException("Failed to encode NetworkNode id = `" + idPrivate + "`: " + msg);

			})
			// finally if we have data
			.ifPresent(encoded -> {
				// and if that data is a NBTCompound (fundamental element like an int, but for groups of NBT elements)
				if (encoded instanceof NbtCompound compound) {
					nbt.copyFrom(compound);
				}
			});

		return nbt;
	}

	/**
	 * @param nbt
	 *
	 * Generates a new {@link NetworkNode} from a {@link NbtCompound}.
	 *
	 * <p>
	 * Does not assign to a network, that will be done by {@link Network} when it's generated from NBT.
	 * </p>
	 *
	 * @return the new {@link NetworkNode} object.
	 */
	public static NetworkNode fromNbt(NbtCompound nbt) {
		return CODEC
				// use the codec to parse from NBT to object
				.parse(NbtOps.INSTANCE, nbt)
				// if the result doesn't look right we throw an error.
				.resultOrPartial(msg -> {
					throw new IllegalStateException("Failed to decode NetworkNode: " + msg);
				})
				// if we failed we throw an error, otherwise return the result.
		        .orElseThrow(() -> new IllegalStateException("Failed to decode NetworkNode"));
	}
}
