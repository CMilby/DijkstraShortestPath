package me.cmilby;

import java.util.List;

public class Graph {

	public List<Node> nodes;
	public List<Edge> edges;

	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public List<Node> getVertexes() {
		return nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}
}