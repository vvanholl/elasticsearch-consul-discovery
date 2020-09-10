package consul.model.health;



import com.google.gson.annotations.*;

/**
 * Consul node class.
 */
public class Node {
    @SerializedName("Address")
    private String address;

    @SerializedName("Node")
    private String node;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;

        if (address != null ? !address.equals(node.address) : node.address != null) {
            return false;
        }
        return !(this.node != null ? !this.node.equals(node.node) : node.node != null);
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (node != null ? node.hashCode() : 0);
        return result;
    }
}
