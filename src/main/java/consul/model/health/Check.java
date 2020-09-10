package consul.model.health;



import com.google.gson.annotations.*;

/**
 * Consul check class.
 */
public class Check {
    @SerializedName("CheckID")
    private String checkID;

    @SerializedName("Name")
    private String name;

    @SerializedName("Node")
    private String node;

    @SerializedName("Notes")
    private String notes;

    @SerializedName("Output")
    private String output;

    @SerializedName("ServiceID")
    private String serviceID;

    @SerializedName("ServiceName")
    private String serviceName;

    @SerializedName("Status")
    private String status;

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Check check = (Check) o;

        if (checkID != null ? !checkID.equals(check.checkID) : check.checkID != null) {
            return false;
        }
        if (name != null ? !name.equals(check.name) : check.name != null) {
            return false;
        }
        if (node != null ? !node.equals(check.node) : check.node != null) {
            return false;
        }
        if (notes != null ? !notes.equals(check.notes) : check.notes != null) {
            return false;
        }
        if (output != null ? !output.equals(check.output) : check.output != null) {
            return false;
        }
        if (serviceID != null ? !serviceID.equals(check.serviceID) : check.serviceID != null) {
            return false;
        }
        if (serviceName != null ? !serviceName.equals(check.serviceName) : check.serviceName != null) {
            return false;
        }
        if (status != null ? !status.equals(check.status) : check.status != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = checkID != null ? checkID.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (node != null ? node.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        result = 31 * result + (serviceID != null ? serviceID.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
