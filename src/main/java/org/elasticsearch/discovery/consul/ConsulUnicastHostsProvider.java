package org.elasticsearch.discovery.consul;
/**
 * Copyright © 2015 Lithium Technologies, Inc. All rights reserved subject to the terms of
 * the MIT License located at
 *
 *
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
 *
 *
 *
 * Created by Jigar Joshi on 8/9/15.
 */

import static java.util.Collections.emptyList;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Setting.Property;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.discovery.zen.UnicastHostsProvider;
import org.elasticsearch.transport.TransportService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import consul.model.DiscoveryResult;
import consul.service.ConsulService;

/**
 * Consul unicast host provider class.
 */
public class ConsulUnicastHostsProvider extends AbstractComponent implements UnicastHostsProvider {

    public static final Setting<String> CONSUL_LOCALWSHOST = Setting.simpleString("discovery.consul.local-ws-host",
            Property.NodeScope);
    public static final Setting<Integer> CONSUL_LOCALWSPORT = Setting.intSetting("discovery.consul.local-ws-port", 8500,
            Property.NodeScope);
    public static final Setting<List<String>> CONSUL_SERVICENAMES = Setting.listSetting("discovery.consul.service-names",
            emptyList(), Function.identity(), Property.NodeScope);
    public static final Setting<String> CONSUL_TAG = Setting.simpleString("discovery.consul.tag", Property.NodeScope);
    public static final Setting<Boolean> CONSUL_HEALTHY = Setting.boolSetting("discovery.consul.healthy", true,
            Property.NodeScope);

    private final TransportService transportService;
    private final Set<String> consulServiceNames;
    private final String consulAgentLocalWebServiceHost;
    private final int consulAgentLocalWebServicePort;
    private final String tag;
    private final boolean healthy;

    public ConsulUnicastHostsProvider(Settings settings, TransportService transportService) {
        this.transportService = transportService;

        this.consulServiceNames = new HashSet<>();
        for (String serviceName : CONSUL_SERVICENAMES.get(settings)) {
            this.consulServiceNames.add(serviceName);
        }

        this.consulAgentLocalWebServiceHost = CONSUL_LOCALWSHOST.get(settings);
        this.consulAgentLocalWebServicePort = CONSUL_LOCALWSPORT.get(settings);
        this.tag = CONSUL_TAG.get(settings);
        this.healthy = CONSUL_HEALTHY.get(settings);
    }

    @Override
    public List<TransportAddress> buildDynamicHosts(HostsResolver hostsResolver) {
        logger.debug("Discovering nodes");

        List<TransportAddress> discoNodes = new ArrayList<TransportAddress>();
        Set<DiscoveryResult> consulDiscoveryResults = null;

        try {
            logger.debug("Starting discovery request");
            final long startTime = System.currentTimeMillis();
            consulDiscoveryResults = new ConsulService(this.consulAgentLocalWebServiceHost,
                    this.consulAgentLocalWebServicePort, this.tag, this.healthy).discoverNodes(this.consulServiceNames);
            logger.debug("Discovered {} nodes", (consulDiscoveryResults != null ? consulDiscoveryResults.size() : 0));
            logger.debug("{} ms it took for discovery request", (System.currentTimeMillis() - startTime));
        } catch (IOException ioException) {
            logger.error("Failed to discover nodes, failed in making consul based " + "discovery", ioException);
        } catch (java.security.PrivilegedActionException privilegeException){
            logger.error("Failed to discover nodes, due to security privileges", privilegeException);
        }

        if (consulDiscoveryResults != null) {
            consulDiscoveryResults.stream().forEach(discoveryResult -> {
                String address;
                if (discoveryResult.getIp().contains(":")) {
                    address = "[" + discoveryResult.getIp() + "]:" + discoveryResult.getPort();
                } else {
                    address = discoveryResult.getIp() + ":" + discoveryResult.getPort();
                }
                try {
                    TransportAddress[] addresses = transportService.addressesFromString(address, 1);
                    logger.debug("Adding {}, transport_address {}", address, addresses[0]);
                    discoNodes.add(addresses[0]);

                } catch (Exception e) {
                    logger.warn("Failed to add {}, address {}", e, address);
                }
            });
        }

        logger.debug("Using Consul based dynamic discovery nodes {}", discoNodes);

        return discoNodes;
    }
}
