package dev.pswg;

import net.jcip.annotations.GuardedBy;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.concurrent.AsSynchronizedGraph;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Network implements AutoCloseable {
	@NotNull
	private final ReadWriteLock _lock = new ReentrantReadWriteLock();

	@NotNull
	private final UUID _id = UUID.randomUUID();

	// For now, we lock networks to a single world as that will make things easer.
	// ideally we want to support multi, dimension ranges, but not during testing.
	@NotNull
	private final RegistryKey<World> _world;

	public static Network Create(@NotNull RegistryKey<World> world){
		Network n = new Network(world, /*private*/ true);
		NetworkTable.reg.put(n._id, n);
		return n;
	}

	private Network(@NotNull RegistryKey<World> world, boolean ignored){
		_world = Objects.requireNonNull(world);
	}

	public static @Nullable Network FromId(@NotNull UUID id){
		Objects.requireNonNull(id, "id");
		return NetworkTable.reg.get(id);
	}


	//                               We use this so we can store the distance between nodes and don't have to calculate every time. Might not be useful.
	//                               If not useful, move to DefaultEdge class and SimpleGraph class.
	@NotNull
	private final Graph<NetworkNode, DefaultWeightedEdge> _graph = new AsSynchronizedGraph<>(new SimpleWeightedGraph<>(DefaultWeightedEdge.class));

	@NotNull
	public UUID get_id(){
		return _id;
	}

	/**
	 * Connects two {@link NetworkNode}s.
	 *
	 * <p>The nodes will be added to the network automatically if they are not
	 * already present. A connection is only permitted if at least one node’s
	 * range contains the other node’s position.</p>
	 *
	 * <p>The edge weight is set to the <strong>squared Euclidean distance</strong>
	 * between the two node positions, measured in block coordinates.</p>
	 *
	 * @param a the first node to connect
	 * @param b the second node to connect
	 * @return the newly created {@link DefaultWeightedEdge}
	 *
	 * @throws IllegalArgumentException if {@code a} and {@code b} refer to the same node
	 * @throws IllegalStateException if the nodes are not within range of each other
	 * @throws IllegalStateException if an edge already exists between the nodes or
	 *                               if the edge cannot be created for some other reason.
	 */
	@NotNull
	public DefaultWeightedEdge Connect(@NotNull NetworkNode a, @NotNull NetworkNode b){
		Objects.requireNonNull(a, "a");
		Objects.requireNonNull(b, "b");

		_lock.writeLock().lock();
		try{
			if (a.equals(b)){
				throw new IllegalArgumentException("Cannot connect a node to itself. a == " + a.GetPos());
			}

			if (!_graph.containsVertex(a)){
				AddNode(a);
			}

			if (!_graph.containsVertex(b)){
				AddNode(b);
			}

			if (!b.GetRange().contains(a.GetPos()) && !a.GetRange().contains(b.GetPos())){
				throw new IllegalStateException(
						"Nodes are not within range of each other. " +
						"a == " + a.GetPos() + ", b == " + b.GetPos()
				);
			}

			BlockPos pa = a.GetPos().pos();
			BlockPos pb = b.GetPos().pos();

			double distance = pa.getSquaredDistance(pb);

			DefaultWeightedEdge edge = _graph.addEdge(a, b);
			if (edge == null){
				throw new IllegalStateException(
						"Edge already exists (or cannot be created) between these nodes. " +
						"a == " + a.GetPos() + ", b == " + b.GetPos()
				);
			}

			_graph.setEdgeWeight(edge, distance);
			return edge;
		}
		finally {
			_lock.writeLock().unlock();
		}
	}

	/**
	 * Removes the connection between two {@link NetworkNode}s, if present.
	 *
	 * <p>If no edge exists between the given nodes, this method performs no action
	 * and returns {@code false}.</p>
	 *
	 * @param a the first node
	 * @param b the second node
	 * @return {@code true} if a connection existed and was removed; {@code false} otherwise
	 * @throws NullPointerException if {@code a} or {@code b} is {@code null}
	 */
	public boolean ClearConnection(@NotNull NetworkNode a, @NotNull NetworkNode b){
		Objects.requireNonNull(a, "a");
		Objects.requireNonNull(b, "b");
		_lock.writeLock().lock();
		try{
			DefaultWeightedEdge edge = _graph.getEdge(a, b);
			if (edge == null){
				return false;
			}

			return _graph.removeEdge(edge);
		}
		finally {
			_lock.writeLock().unlock();
		}
	}



	/**
	 * Adds a {@link NetworkNode} to this network.
	 *
	 * <p>The node must be in the same world as the network and must not already
	 * belong to another network. If these conditions are met, it will be added
	 * and associated with this network.</p>
	 *
	 * <p>Upon successful addition, the node’s network identifier is set to this
	 * network’s ID, and the node is indexed into the network’s range map.</p>
	 *
	 * @param node the node to add
	 *
	 * @throws NullPointerException if {@code node} is {@code null}
	 * @throws IllegalArgumentException if the node’s dimension does not match the network’s world
	 * @throws IllegalStateException if the node already belongs to a network
	 * @throws IllegalStateException if the node already exists in the graph
	 */
	public void AddNode(@NotNull NetworkNode node){
		Objects.requireNonNull(node, "node");
		_lock.writeLock().lock();
		try {
			AddNodeInternal(node);
		} finally {
			_lock.writeLock().unlock();
		}
	}

	/**
	 * Adds a {@link NetworkNode} to this network.
	 *
	 * <p><b color=Red>Thread-Safety Warning:</b> This method is <b>not thread-safe</b> on its own.
	 * The caller <b>must</b> hold the network’s write lock before invoking this method.
	 * Failing to do so may result in data races and corrupted internal state.</p>
	 */
	@GuardedBy("_lock")
	private void AddNodeInternal(@NotNull NetworkNode node){
		assert ((ReentrantReadWriteLock)_lock).isWriteLockedByCurrentThread()
				: "AddNodeInternal must be called while holding the write lock";

		Objects.requireNonNull(node, "node");
		if (!node.GetDimension().equals(_world)){
			throw new IllegalArgumentException("Node dimension does not match Network world.");
		}

		if (node.GetNetworkId() != null){
			throw new IllegalStateException(
					"Node already belongs to a network. networkId == " + node.GetNetworkId()
			);
		}

		if (!_graph.addVertex(node)){
			throw new IllegalStateException("Node already exists in graph.");
		}

		if (!node.SetNetwork(_id))
			throw new AssertionError("We just checked that the node has no network, now its network is " + node.GetNetworkId());


		Set<GlobalPos> range = node.GetRange();
		for (GlobalPos pos : range){
			List<NetworkNode> nodesHere = _rangeData.computeIfAbsent(pos, k -> new ArrayList<>());

			if (!nodesHere.contains(node)){
				nodesHere.add(node);
			}
		}
	}

	/**
	 * Removes a {@link NetworkNode} from this network.
	 *
	 * <p>If the node is present in the network, it will be removed along with all
	 * range-index entries that reference it. Any positions that no longer reference
	 * any nodes will be removed from the range index.</p>
	 *
	 * <p>If the node is not present in the network, this method performs no action
	 * and returns {@code false}.</p>
	 *
	 * @param node the node to remove
	 * @return {@code true} if the node was present and removed; {@code false} otherwise
	 *
	 * @throws NullPointerException if {@code node} is {@code null}
	 */
	public boolean RemoveNode(@NotNull NetworkNode node){
		Objects.requireNonNull(node, "node");
		_lock.writeLock().lock();
		try {

			boolean removed = _graph.removeVertex(node);
			if (!removed){
				return false;
			}

			// Update range index + range cache
			Set<GlobalPos> range = node.GetRange();
			for (GlobalPos pos : range){
				List<NetworkNode> nodesHere = _rangeData.get(pos);
				if (nodesHere != null){
					nodesHere.remove(node);

					if (nodesHere.isEmpty()){
						_rangeData.remove(pos);
					}
				}
			}

			return true;

		} finally {
			_lock.writeLock().unlock();
		}
	}

	@NotNull
	private final ConcurrentHashMap<GlobalPos, List<NetworkNode>> _rangeData = new ConcurrentHashMap<>();

	/**
	 * Returns the set of all block positions covered by this network.
	 *
	 * <p>The returned set represents the union of the ranges of all nodes currently
	 * present in the network. Each {@link GlobalPos} in the set corresponds to at
	 * least one {@link NetworkNode} whose range includes that position.</p>
	 *
	 * @return an immutable set of {@link GlobalPos} values representing the network’s coverage
	 */
	@NotNull
	public Set<GlobalPos> GetRange(){
		_lock.readLock().lock();
		try{
			return Set.copyOf(_rangeData.keySet());
		}
		finally {
			_lock.readLock().unlock();
		}
	}

	/**
	 * Returns the network nodes whose range includes the given position.
	 *
	 * <p>If no nodes in this network cover the specified position, an empty set
	 * is returned.</p>
	 *
	 * @param pos the global block position to query
	 * @return an immutable set of {@link NetworkNode}s whose range includes {@code pos}
	 *
	 * @throws NullPointerException if {@code pos} is {@code null}
	 */
	@NotNull
	public Set<NetworkNode> NodesAt(@NotNull GlobalPos pos){
		Objects.requireNonNull(pos, "pos");

		_lock.readLock().lock();
		try{
			List<NetworkNode> nodesHere = _rangeData.get(pos);
			if (nodesHere == null){
				return Set.of();
			}
			return Set.copyOf(nodesHere);
		} finally {
			_lock.readLock().unlock();
		}
	}

	@Override
	public void close() throws Exception {
		NetworkTable.reg.remove(_id);
	}
}
