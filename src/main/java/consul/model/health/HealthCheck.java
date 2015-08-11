
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

}
