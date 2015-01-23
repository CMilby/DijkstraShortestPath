package me.cmilby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dijkstra {

	// private List<Node> nodes;
	private List<Edge> edges;

	public Dijkstra(List<Node> nodes, List<Edge> edges) {
		// this.nodes = new ArrayList<Node>(nodes);
		this.edges = new ArrayList<Edge>(edges);
	}

	public List<Node> getShortestPath(Node source, Node target) {
		Map<Node, Integer> distances = new HashMap<Node, Integer>();
		Map<Node, Boolean> visited = new HashMap<Node, Boolean>();

		List<Node> path = new ArrayList<Node>();

		Node current = source;
		distances.put(source, 0);
		visited.put(source, true);
		path.add(source);

		while (!current.equals(target)) {
			for (Map.Entry<Node, Integer> node : getNeighbors(current).entrySet()) {
				if (visited.containsKey(node)) {
					distances.put(node.getKey(), ((node.getValue() + distances.get(current) < distances.get(node)) ?
							node.getValue() + distances.get(current) : distances.get(node)));
				} else {
					distances.put(node.getKey(), node.getValue());
					visited.put(node.getKey(), true);
				}
			}
			current = getSmallestNotVisited(getNeighbors(current), visited);
			path.add(current);
		}
		return path;
	}

	public Node getSmallest(Map<Node, Integer> map, List<Node> list) {
		Map.Entry<Node, Integer> smallest = null;
		for (Map.Entry<Node, Integer> node : map.entrySet()) {
			if (smallest == null && list.contains(node))
				smallest = node;
			else if (smallest.getValue() > node.getValue() && list.contains(node))
				smallest = node;
		}
		return smallest.getKey();
	}

	public Map<Node, Integer> getNeighbors(Node node) {
		Map<Node, Integer> neighbors = new HashMap<Node, Integer>();
		for (Edge e : edges) {
			if (e.source.equals(node))
				neighbors.put(e.destination, e.weight);
			else if (e.destination.equals(node))
				neighbors.put(e.source, e.weight);
		}
		return neighbors;
	}

	public Node getSmallestNotVisited(Map<Node, Integer> map, Map<Node, Boolean> visited) {
		Map.Entry<Node, Integer> smallest = null;
		for (Map.Entry<Node, Integer> node : map.entrySet()) {
			if (smallest == null)
				smallest = node;
			else if (node.getValue() < smallest.getValue() && !visited.containsKey(node))
				smallest = node;
		}
		return smallest.getKey();
	}
}
