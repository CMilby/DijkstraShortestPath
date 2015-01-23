package me.cmilby;

public class Edge  {

	public String id;

	public Node source;
	public Node destination;

	public int weight;

	public boolean isHighlighted;

	public Edge(String id, Node source, Node destination, int weight) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.isHighlighted = false;
	}
}