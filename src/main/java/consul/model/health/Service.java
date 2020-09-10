package consul.model.health;


import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Consul service class.
 */
public class Service {
    @SerializedName("Address")
    private String address;

    @SerializedName("ID")
    private String id;

    @SerializedName("Port")
    private Integer port;

    @SerializedName("Service")
    private String service;

    @SerializedName("Tags")
    private List<String> tags = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Service service = (Service) o;

        if (address != null ? !address.equals(service.address) : service.address != null) {
            return false;
        }
        if (id != null ? !id.equals(service.id) : service.id != null) {
            return false;
        }
        if (port != null ? !port.equals(service.port) : service.port != null) {
            return false;
        }
        if (this.service != null ? !this.service.equals(service.service) : service.service != null) {
            return false;
        }
        if (tags != null ? !tags.equals(service.tags) : service.tags != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
