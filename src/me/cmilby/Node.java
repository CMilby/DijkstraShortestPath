package me.cmilby;

public class Node {

	public String id;

	public int x;
	public int y;

	public boolean isHighlighted;

	public Node(String id) {
		this(id, 0, 0);
	}

	public Node(String id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.isHighlighted = false;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id;
	}
}
