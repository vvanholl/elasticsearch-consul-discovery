/*
 * Copyright (c) 2015 Grant Rodgers
 *
 *     Permission is hereby granted, free of charge, to any person obtaining
 *     a copy of this software and associated documentation files (the "Software"),
 *     to deal in the Software without restriction, including without limitation
 *     the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *     and/or sell copies of the Software, and to permit persons to whom the Software
 *     is furnished to do so, subject to the following conditions:
 *
 *     The above copyright notice and this permission notice shall be included in
 *     all copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *     EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *     OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *     IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *     CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *     TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 *     OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.elasticsearch.discovery.consul;

import consul.model.DiscoveryResult;
import consul.service.ConsulService;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.discovery.zen.ping.unicast.UnicastHostsProvider;
import org.elasticsearch.transport.TransportService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class ConsulUnicastHostsProvider extends AbstractComponent implements UnicastHostsProvider {

	private final TransportService transportService;
	private final Version version;
	private final String consulServiceName;
	private final int consulAgentLocalWebServicePort;
	private final String tag;

	@Inject
	public ConsulUnicastHostsProvider(Settings settings, TransportService
			transportService, Version version) {
		super(settings);
		this.transportService = transportService;
		this.version = version;
		this.consulServiceName = settings.get("discovery.consul.service-name");
		this.consulAgentLocalWebServicePort = settings.getAsInt("discovery.consul" +
				".local-ws-port", 8500);
		this.tag = settings.get("discovery.consul.tag");
	}

	@Override
	public List<DiscoveryNode> buildDynamicNodes() {
		if (logger.isTraceEnabled()) {
			logger.trace("discovering nodes");
		}
		List<DiscoveryNode> discoNodes = Lists.newArrayList();
		Set<DiscoveryResult> consulDiscoveryResults = null;
		try {
			consulDiscoveryResults = new ConsulService(this
					.consulAgentLocalWebServicePort, this.tag)
					.discoverHealthyNodes(this.consulServiceName);
		} catch (IOException ioException) {
			logger.error("Failed to discover nodes, failed in making consul based " +
					"discovery", ioException);
		}
		if (consulDiscoveryResults != null) {
			consulDiscoveryResults.stream().forEach(discoveryResult -> {
				String address = discoveryResult.getIp() + ":" + discoveryResult.getPort();
				try {
					TransportAddress[] addresses = transportService.addressesFromString(address);
					logger.trace("adding {}, transport_address {}", address, addresses[0]);
					discoNodes.add(new DiscoveryNode("#consul-" + address, addresses[0],
							version.minimumCompatibilityVersion()));
				} catch (Exception e) {
					logger.warn("failed to add {}, address {}", e, address);
				}
			});
		}
		if (logger.isDebugEnabled()) {
			logger.debug("using consul based dynamic discovery nodes {}", discoNodes);
		}
		return discoNodes;
	}
}
