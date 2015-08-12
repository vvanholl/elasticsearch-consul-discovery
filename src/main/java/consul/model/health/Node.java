
package consul.model.health;

public class Node {

	private String Address;
	private String Node;

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getNode() {
		return Node;
	}

	public void setNode(String Node) {
		this.Node = Node;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Node node = (Node) o;

		if (Address != null ? !Address.equals(node.Address) : node.Address != null)
			return false;
		return !(Node != null ? !Node.equals(node.Node) : node.Node != null);

	}

	@Override
	public int hashCode() {
		int result = Address != null ? Address.hashCode() : 0;
		result = 31 * result + (Node != null ? Node.hashCode() : 0);
		return result;
	}
}
