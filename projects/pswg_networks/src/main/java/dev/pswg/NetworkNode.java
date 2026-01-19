package dev.pswg;

import dev.pswg.util.NetworkDefaults;
import net.jcip.annotations.GuardedBy;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;

public class NetworkNode implements AutoCloseable {
	@NotNull
	private final UUID idPrivate = UUID.randomUUID();

	//TODO: make this data driven instead of hard coded.
	@SuppressWarnings("FieldCanBeLocal")
	private final int rangePrivate = 10;

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
		NetworkNode output = new NetworkNode(location);
		NetworkTable.NodesPackagePrivate.put(output.idPrivate, output);
		return output;
	}

	private NetworkNode(@NotNull GlobalPos location){
		Objects.requireNonNull(location);
		locationPrivate = location;
	}

	private final BiFunction<@NotNull Integer, @NotNull NetworkNode, @NotNull Set<GlobalPos>> rangeFinderPrivate = NetworkDefaults.DefaultRangeFinder;

	public Set<GlobalPos> GetRange(){
		return rangeFinderPrivate.apply(rangePrivate, this);
	}
	@Override
	public void close() {
		NetworkTable.NodesPackagePrivate.remove(idPrivate);
	}
}
