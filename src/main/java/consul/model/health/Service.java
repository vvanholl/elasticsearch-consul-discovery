
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

}
