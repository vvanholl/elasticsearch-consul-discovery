package consul.service;
/**
 * Copyright © 2015 Lithium Technologies, Inc. All rights reserved subject to the terms of
 * the MIT License located at
 * <p/>
 * <p/>
 * LICENSE FILE DISCLOSURE STATEMENT AND COPYRIGHT NOTICE This LICENSE.txt file sets forth
 * the general licensing terms and attributions for the “Elasticsearch Consul Discovery
 * plugin” project software provided by Lithium Technologies, Inc. (“Lithium”).  This
 * software is based upon certain software files authored by Grant Rodgers in 2015 as part
 * of the “Elasticsearch SRV discovery plugin” project.  As a result, some of the files in
 * this Elasticsearch Consul Discovery plugin” project software are wholly authored by
 * Lithium, some are wholly authored by Grant Rodgers, and others are originally authored
 * by Grant Rodgers and subsequently modified by Lithium.  Files that were either modified
 * or wholly authored by Lithium contain an additional LICENSE.txt file indicating whether
 * they were modified or wholly authored by Lithium.  Any LICENSE.txt files, copyrights or
 * attribution originally included in the files of the original “Elasticsearch SRV
 * discovery plugin” remain unchanged. Copyright Notices The following copyright notice
 * applies to only those files and modifications authored by Lithium: Copyright © 2015
 * Lithium Technologies, Inc.  All rights reserved subject to the terms of the MIT License
 * below. The following copyright notice applies to only those files in the original
 * “Elasticsearch SRV discovery plugin” project software excluding any modifications by
 * Lithium: Copyright (c) 2015 Grant Rodgers License The following MIT License, as
 * originally presented in the “Elasticsearch SRV discovery plugin” project software, also
 * applies to files authored or modified by Lithium.  Your use of the “Elasticsearch
 * Consul Discovery plugin” project software is therefore subject to the terms of the
 * following MIT License.  Except as may be granted herein or by separate express written
 * agreement, this file provides no license to any Lithium patents, trademarks,
 * copyrights, or other intellectual property. MIT License Permission is hereby granted,
 * free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to
 * whom the Software is furnished to do so, subject to the following conditions: The above
 * copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT
 * WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * <p/>
 * <p/>
 * <p/>
 */

import com.google.gson.Gson;
import consul.model.DiscoveryResult;
import consul.model.health.HealthCheck;
import utils.Utility;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

/**
 * Uses consul's REST API and exposes certain functionality
 * <p/>
 * Created by Jigar Joshi on 8/9/15.
 */

public final class ConsulService {
	private static final String CONSUL_HEALTH_CHECK_API_ENDPOINT_TEMPLATE =
			"http://localhost:%d/v1/health/service/%s?%s";

	private final int consulAgentLocalWebServicePort;
	private final String tag;

	/**
	 * @param consulPort port where the consul agent on node exposes HTTP API, by default
	 *                   it is 8500
	 * @param tag        if not null it will filter query on the tags
	 */
	public ConsulService(final int consulPort, final String tag) {
		this.consulAgentLocalWebServicePort = consulPort;
		this.tag = tag;
	}

	/**
	 * Communicates to consul over consul's REST API and retrieves list of healthy node's
	 * IPs & port detail for the given service name
	 * <p/>
	 * It does it by making HTTP API call to
	 * <p/>
	 * http://localhost:${consulAgentLocalWebServicePort}/v1/health/service/${serviceName
	 * }?${queryParams}
	 * <p/>
	 * queryParams is ?passing by default if tag holds not null value it would be
	 * ?passing&tag=${tag}
	 *
	 * @param serviceNames
	 * @return non null Set<DiscoveryResult> Containing IPs & port detail of healthy nodes
	 * for given serviceName
	 */
	public Set<DiscoveryResult> discoverHealthyNodes(Set<String> serviceNames) throws
			IOException {
		Set<DiscoveryResult> result = new HashSet<>();
		for (String serviceName : serviceNames) {
			String consulServiceHealthEndPoint = getConsulHealthCheckApiUrl(serviceName);
			final String apiResponse = Utility.readUrl(consulServiceHealthEndPoint);
			HealthCheck[] healthChecks = new Gson().fromJson(apiResponse, HealthCheck[].class);

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
