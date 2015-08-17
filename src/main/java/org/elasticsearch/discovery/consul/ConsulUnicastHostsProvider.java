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
 * Created by Jigar Joshi on 8/9/15.
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
