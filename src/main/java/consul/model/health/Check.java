
package consul.model.health;

public class Check {

	private String CheckID;
	private String Name;
	private String Node;
	private String Notes;
	private String Output;
	private String ServiceID;
	private String ServiceName;
	private String Status;

	public String getCheckID() {
		return CheckID;
	}

	public void setCheckID(String CheckID) {
		this.CheckID = CheckID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getNode() {
		return Node;
	}

	public void setNode(String Node) {
		this.Node = Node;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String Notes) {
		this.Notes = Notes;
	}

	public String getOutput() {
		return Output;
	}

	public void setOutput(String Output) {
		this.Output = Output;
	}

	public String getServiceID() {
		return ServiceID;
	}

	public void setServiceID(String ServiceID) {
		this.ServiceID = ServiceID;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String ServiceName) {
		this.ServiceName = ServiceName;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

}
