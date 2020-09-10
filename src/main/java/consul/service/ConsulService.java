package consul.service;


import com.google.gson.Gson;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import consul.model.DiscoveryResult;
import consul.model.health.HealthCheck;
import utils.Utility;

/**
 * Uses consul's REST API and exposes certain functionality.
 */
public final class ConsulService {
    private static final String CONSUL_HEALTH_CHECK_API_ENDPOINT_TEMPLATE = "%s:%d/v1/health/service/%s?%s";

    private final String consulAgentLocalHostname;
    private final int consulAgentLocalWebServicePort;
    private final String tag;
    private final boolean healthy;

    /**
     * @param consulHost host where the consul agent on node exposes HTTP API, by default
     *                   it is http://localhost
     * @param consulPort port where the consul agent on node exposes HTTP API, by default
     *                   it is 8500
     * @param tag        if not null it will filter query on the tags
     * @param healthy    [true|false] (default: true) determines if service should be healthy
     *                   to be discovered
     */
    public ConsulService(final String consulHost, final int consulPort, final String tag, final boolean healthy) {
        this.consulAgentLocalWebServicePort = consulPort;
        this.consulAgentLocalHostname = consulHost.isEmpty() ? "http://localhost" : consulHost;
        this.tag = tag;
        this.healthy = healthy;
    }

    /**
     * Communicates to consul over consul's REST API and retrieves list of node's
     * IPs and port detail for the given service name and health status.
     * <p>
     * It does it by making HTTP API call to
     * <p>
     * http://${consulAgentLocalHostname}:${consulAgentLocalWebServicePort}/v1/health/service/${serviceName
     * }?${queryParams}
     * <p>
     * queryParams is ?passing=true (false if discovery.consul.healthy: false) by default
     * If tag holds not null value it would be {@literal ?passing=${healthy}&tag=${tag}}
     *
     * @param serviceNames Consul service names given for discovery
     * @return the IPs and port detail of nodes for given serviceName
     * @throws IOException when communication with Consul fails
     * @throws java.security.PrivilegedActionException when security issues
     */
    public Set<DiscoveryResult> discoverNodes(Set<String> serviceNames) throws
            IOException, java.security.PrivilegedActionException {
        Set<DiscoveryResult> result = new HashSet<>();
        for (String serviceName : serviceNames) {
            String consulServiceHealthEndPoint = getConsulHealthCheckApiUrl(serviceName);
            final String apiResponse = Utility.readUrl(consulServiceHealthEndPoint);
            HealthCheck[] healthChecks = (HealthCheck[]) AccessController.doPrivileged(
                    new PrivilegedAction<HealthCheck[]>() {
                        @Override
                        public HealthCheck[] run() {
                            return new Gson().fromJson(apiResponse, HealthCheck[].class);
                        }
                    }
            );
            Arrays.stream(healthChecks).forEach(healthCheck -> {
                String ip = healthCheck.getService().getAddress();
                int port = healthCheck.getService().getPort();
                if (ip == null || ip.isEmpty()) {
                    ip = healthCheck.getNode().getAddress();
                }
                result.add(new DiscoveryResult(ip, port));
            });
        }
        return result;
    }


    private final String getConsulHealthCheckApiUrl(final String serviceName) {
        final StringBuffer queryParam = new StringBuffer(String.format("passing=%b", this.healthy));
        if ((this.tag != null) && (!this.tag.equals(""))) {
            queryParam.append("&tag=");
            queryParam.append(tag.trim());
        }
        return String.format(CONSUL_HEALTH_CHECK_API_ENDPOINT_TEMPLATE,
                consulAgentLocalHostname, consulAgentLocalWebServicePort, serviceName,
                queryParam.toString());
    }
}
