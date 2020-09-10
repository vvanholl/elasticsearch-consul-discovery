package consul.model.health;


import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Consul health check class.
 */
public class HealthCheck {
    @SerializedName("Checks")
    private List<Check> checks = new ArrayList<>();

    @SerializedName("Node")
    private consul.model.health.Node node;

    @SerializedName("Service")
    private consul.model.health.Service service;

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    public consul.model.health.Node getNode() {
        return node;
    }

    public void setNode(consul.model.health.Node node) {
        this.node = node;
    }

    public consul.model.health.Service getService() {
        return service;
    }

    public void setService(consul.model.health.Service service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HealthCheck that = (HealthCheck) o;

        if (checks != null ? !checks.equals(that.checks) : that.checks != null) {
            return false;
        }
        if (node != null ? !node.equals(that.node) : that.node != null) {
            return false;
        }
        if (service != null ? !service.equals(that.service) : that.service != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = checks != null ? checks.hashCode() : 0;
        result = 31 * result + (node != null ? node.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        return result;
    }
}
