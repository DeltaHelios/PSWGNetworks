package dev.pswg.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.pswg.NetworkNode;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.concurrent.AsSynchronizedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Graphs {

	public record EdgeData(int aIndex, int bIndex, double distance) {
		public static final String NodeAIndexKey = "node_a_index";
		public static final String NodeBIndexKey = "node_b_index";

		public static final String WeightKey = "distance";

		public static final Codec<EdgeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.INT.fieldOf(NodeAIndexKey).forGetter(EdgeData::aIndex),
				Codec.INT.fieldOf(NodeBIndexKey).forGetter(EdgeData::bIndex),
				Codec.DOUBLE.fieldOf(WeightKey).forGetter(EdgeData::distance)
		).apply(instance, EdgeData::new));
	}

	public record GraphData(List<NetworkNode> nodes, List<EdgeData> edges) {
		public static final String NodeListKey = "nodes";

		public static final String EdgeDataKey = "edges";

		public static final Codec<GraphData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				NetworkNode.CODEC.listOf().fieldOf(NodeListKey).forGetter(GraphData::nodes),
				EdgeData.CODEC.listOf().fieldOf(EdgeDataKey).forGetter(GraphData::edges)
		).apply(instance, GraphData::new));
	}

	private static GraphData encodeGraph(Graph<NetworkNode, DefaultWeightedEdge> graph) {
		List<NetworkNode> nodes = new ArrayList<>(graph.vertexSet());

		Map<NetworkNode, Integer> indexByNode = new HashMap<>();
		for (int i = 0; i < nodes.size(); i++) {
			indexByNode.put(nodes.get(i), i);
		}

		List<EdgeData> edges = new ArrayList<>();
		for (DefaultWeightedEdge edge : graph.edgeSet()) {
			NetworkNode a = graph.getEdgeSource(edge);
			NetworkNode b = graph.getEdgeTarget(edge);


			Integer aIndexObj = indexByNode.get(a);
			Integer bIndexObj = indexByNode.get(b);

			if (aIndexObj == null || bIndexObj == null) {
				throw new IllegalStateException("Edge references a node that is not in vertex set.");
			}

			int aIndex = aIndexObj;
			int bIndex = bIndexObj;


			double weight = graph.getEdgeWeight(edge);

			edges.add(new EdgeData(aIndex, bIndex, weight));
		}

		return new GraphData(nodes, edges);
	}


	private static AsSynchronizedGraph<NetworkNode, DefaultWeightedEdge> decodeGraph(GraphData data) {
		AsSynchronizedGraph<NetworkNode, DefaultWeightedEdge> base =
				new AsSynchronizedGraph<>(new SimpleWeightedGraph<>(DefaultWeightedEdge.class));

		List<NetworkNode> nodes = data.nodes();
		for (NetworkNode node : nodes) {
			base.addVertex(node);
		}

		List<EdgeData> edges = data.edges();
		for (EdgeData e : edges) {
			int a = e.aIndex();
			int b = e.bIndex();

			if (a < 0 || a >= nodes.size() ) {
				throw new IllegalStateException("Edge aIndex out of bounds: (A: " + a + ", B: " + b + "), " + nodes.size());
			}

			if (b < 0 || b >= nodes.size() ) {
				throw new IllegalStateException("Edge bIndex out of bounds: (A: " + a + ", B: " + b + "), " + nodes.size());
			}

			NetworkNode nodeA = nodes.get(a);
			NetworkNode nodeB = nodes.get(b);

			DefaultWeightedEdge added = base.addEdge(nodeA, nodeB);
			if (added == null) {
				throw new IllegalStateException(
						"Duplicate or invalid edge between indices: (" + a + ", " + b + ")"
				);
			}

			base.setEdgeWeight(added, e.distance());
		}

		return base;
	}


	public static final Codec<AsSynchronizedGraph<NetworkNode, DefaultWeightedEdge>> CODEC =
			GraphData.CODEC.xmap(
					Graphs::decodeGraph,
					Graphs::encodeGraph
			);
}
