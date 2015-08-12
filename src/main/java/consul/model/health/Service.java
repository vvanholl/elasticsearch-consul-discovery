
package consul.model.health;

import java.util.ArrayList;
import java.util.List;

public class Service {

	private String Address;
	private String ID;
	private Integer Port;
	private String Service;
	private List<String> Tags = new ArrayList<>();

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public Integer getPort() {
		return Port;
	}

	public void setPort(Integer Port) {
		this.Port = Port;
	}

	public String getService() {
		return Service;
	}


	public void setService(String Service) {
		this.Service = Service;
	}

	public List<String> getTags() {
		return Tags;
	}

	public void setTags(List<String> Tags) {
		this.Tags = Tags;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Service service = (Service) o;

		if (Address != null ? !Address.equals(service.Address) : service.Address != null)
			return false;
		if (ID != null ? !ID.equals(service.ID) : service.ID != null) return false;
		if (Port != null ? !Port.equals(service.Port) : service.Port != null)
			return false;
		if (Service != null ? !Service.equals(service.Service) : service.Service != null)
			return false;
		if (Tags != null ? !Tags.equals(service.Tags) : service.Tags != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = Address != null ? Address.hashCode() : 0;
		result = 31 * result + (ID != null ? ID.hashCode() : 0);
		result = 31 * result + (Port != null ? Port.hashCode() : 0);
		result = 31 * result + (Service != null ? Service.hashCode() : 0);
		result = 31 * result + (Tags != null ? Tags.hashCode() : 0);
		return result;
	}
}
