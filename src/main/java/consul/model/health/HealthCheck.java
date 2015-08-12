
package consul.model.health;

import java.util.ArrayList;
import java.util.List;

public class HealthCheck {

	private List<Check> Checks = new ArrayList<>();
	private consul.model.health.Node Node;
	private consul.model.health.Service Service;

	public List<Check> getChecks() {
		return Checks;
	}

	public void setChecks(List<Check> Checks) {
		this.Checks = Checks;
	}

	public consul.model.health.Node getNode() {
		return Node;
	}

	public void setNode(consul.model.health.Node Node) {
		this.Node = Node;
	}

	public consul.model.health.Service getService() {
		return Service;
	}

	public void setService(consul.model.health.Service Service) {
		this.Service = Service;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HealthCheck that = (HealthCheck) o;

		if (Checks != null ? !Checks.equals(that.Checks) : that.Checks != null)
			return false;
		if (Node != null ? !Node.equals(that.Node) : that.Node != null) return false;
		if (Service != null ? !Service.equals(that.Service) : that.Service != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = Checks != null ? Checks.hashCode() : 0;
		result = 31 * result + (Node != null ? Node.hashCode() : 0);
		result = 31 * result + (Service != null ? Service.hashCode() : 0);
		return result;
	}
}
