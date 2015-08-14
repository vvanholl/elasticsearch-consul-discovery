package consul.service;

import com.google.gson.Gson;
import consul.model.DiscoveryResult;
import consul.model.health.HealthCheck;
import utils.Utility;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Uses consul's REST API and exposes certain functionality
 * Created by jigar.joshi on 8/9/15.
 */
public final class ConsulService {
	private static final String CONSUL_HEALTH_CHECK_API_ENDPOINT_TEMPLATE =
			"http://localhost:%d/v1/health/service/%s?%s";

	private final int consulAgentLocalWebServicePort;
	private final String tag;

	/**
	 * @param consulPort port where the consul agent on node exposes HTTP API, by
	 *                   default it is 8500
	 * @param tag        if not null it will filter query on the tags
	 */
	public ConsulService(final int consulPort, final String tag) {
		this.consulAgentLocalWebServicePort = consulPort;
		this.tag = tag;
	}

	/**
	 * Communicates to consul over consul's REST API and retrieves list of healthy
	 * node's IPs & port detail for the
	 * given service name
	 * <p/>
	 * It does it by making HTTP API call to
	 * <p/>
	 * http://localhost:${consulAgentLocalWebServicePort}/v1/health/service/${serviceName
	 * }?${queryParams}
	 * <p/>
	 * queryParams is ?passing by default if tag holds not null value it would be
	 * ?passing&tag=${tag}
	 *
	 * @param serviceName
	 * @return non null Set<DiscoveryResult> Containing IPs & port detail of healthy
	 * nodes for given serviceName
	 */
	public Set<DiscoveryResult> discoverHealthyNodes(String serviceName) throws
			IOException {
		Set<DiscoveryResult> result = new HashSet<>();
		String consulServiceHealthEndPoint = getConsulHealthCheckApiUrl(serviceName);
		final String apiResponse = Utility.readUrl(consulServiceHealthEndPoint);
		HealthCheck[] healthChecks = new Gson().fromJson(apiResponse,
				HealthCheck[]
						.class);
		Arrays.stream(healthChecks).forEach(healthCheck -> {
			result.add(new DiscoveryResult(healthCheck.getNode().getAddress(), healthCheck
					.getService().getPort()));
		});
		return result;
	}


	private final String getConsulHealthCheckApiUrl(final String serviceName) {
		final StringBuffer queryParam = new StringBuffer("passing");
		if (this.tag != null) {
			queryParam.append("&tag=");
			queryParam.append(tag.trim());
		}
		return String.format(CONSUL_HEALTH_CHECK_API_ENDPOINT_TEMPLATE,
				consulAgentLocalWebServicePort, serviceName,
				queryParam.toString());
	}
}
