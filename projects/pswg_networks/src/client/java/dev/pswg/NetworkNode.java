package dev.pswg;

import dev.pswg.util.NetworkDefaults;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

public class NetworkNode {
	//TODO: make this data driven instead of hard coded.
	@SuppressWarnings("FieldCanBeLocal")
	@NotNull
	private final Integer _range = 10;

	@NotNull
	private final GlobalPos _location;

	/**
	 * The unique identifier of the network this node is a part of.
	 *
	 * <p>This value is {@code null} until the object is assigned to a network.</p>
	 */
	@Nullable
	private UUID _networkId = null;

	/**
	 * Returns the identifier of the network this object belongs to.
	 *
	 * @return the network UUID, or {@code null} if this object is not assigned to any network
	 */
	@Nullable
	public UUID GetNetworkId(){
		return _networkId;
	}

	/**
	 * Assigns this object to a network if it is not already associated with one.
	 *
	 * <p>This method will only succeed once. If a network ID is already set,
	 * the assignment is rejected and the method returns {@code false}.</p>
	 *
	 * @param NetworkID the UUID of the network to assign
	 * @return {@code true} if the network ID was set successfully;
	 *         {@code false} if this object was already assigned to a network
	 */
	public boolean SetNetwork(UUID NetworkID){
		if(_networkId == null){
			_networkId = NetworkID;
			return true;
		}
		else{
			return false;
		}
	}

	@NotNull
	public RegistryKey<World> GetDimension(){
		return _location.dimension();
	}

	@NotNull
	public BlockPos GetBlockPos(){
		return _location.pos();
	}

	@NotNull
	public GlobalPos GetPos(){
		return _location;
	}

	public NetworkNode(@NotNull GlobalPos location){
		Objects.requireNonNull(location);
		_location = location;
	}

	private final BiFunction<@NotNull Integer, @NotNull NetworkNode, @NotNull Set<GlobalPos>> _rangeFinder = NetworkDefaults.DefaultRangeFinder;

	public Set<GlobalPos> GetRange(){
		return _rangeFinder.apply(_range, this);
	}
}
