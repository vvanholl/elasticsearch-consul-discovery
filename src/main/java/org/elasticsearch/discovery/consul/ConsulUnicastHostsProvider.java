package org.elasticsearch.discovery.consul;


import consul.model.DiscoveryResult;
import consul.service.ConsulService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Setting.Property;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.discovery.SeedHostsProvider;
import org.elasticsearch.transport.TransportService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.Collections.emptyList;

/**
 * Consul unicast host provider class.
 */
public class ConsulUnicastHostsProvider implements SeedHostsProvider {

    protected final Logger logger = LogManager.getLogger(getClass());;

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

    public List<TransportAddress> getSeedAddresses(HostsResolver hostsResolver) {
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
                String address = discoveryResult.getIp() + ":" + discoveryResult.getPort();
                try {
                    TransportAddress[] addresses = transportService.addressesFromString(address);
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
