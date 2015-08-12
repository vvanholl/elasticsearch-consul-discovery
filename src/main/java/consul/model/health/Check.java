
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Check check = (Check) o;

		if (CheckID != null ? !CheckID.equals(check.CheckID) : check.CheckID != null)
			return false;
		if (Name != null ? !Name.equals(check.Name) : check.Name != null) return false;
		if (Node != null ? !Node.equals(check.Node) : check.Node != null) return false;
		if (Notes != null ? !Notes.equals(check.Notes) : check.Notes != null)
			return false;
		if (Output != null ? !Output.equals(check.Output) : check.Output != null)
			return false;
		if (ServiceID != null ? !ServiceID.equals(check.ServiceID) : check.ServiceID != null)
			return false;
		if (ServiceName != null ? !ServiceName.equals(check.ServiceName) : check.ServiceName != null)
			return false;
		if (Status != null ? !Status.equals(check.Status) : check.Status != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = CheckID != null ? CheckID.hashCode() : 0;
		result = 31 * result + (Name != null ? Name.hashCode() : 0);
		result = 31 * result + (Node != null ? Node.hashCode() : 0);
		result = 31 * result + (Notes != null ? Notes.hashCode() : 0);
		result = 31 * result + (Output != null ? Output.hashCode() : 0);
		result = 31 * result + (ServiceID != null ? ServiceID.hashCode() : 0);
		result = 31 * result + (ServiceName != null ? ServiceName.hashCode() : 0);
		result = 31 * result + (Status != null ? Status.hashCode() : 0);
		return result;
	}
}
