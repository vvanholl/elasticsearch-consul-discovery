package consul.model;


public final class DiscoveryResult {

    private final String ip;
    private final int port;

    public DiscoveryResult(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiscoveryResult that = (DiscoveryResult) o;

        if (port != that.port) {
            return false;
        }
        return !(ip != null ? !ip.equals(that.ip) : that.ip != null);
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "DiscoveryResult{" + "ip='" + ip + '\'' + ", port=" + port + '}';
    }
}
