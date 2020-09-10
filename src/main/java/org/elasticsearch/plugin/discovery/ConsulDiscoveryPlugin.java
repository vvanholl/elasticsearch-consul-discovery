package org.elasticsearch.plugin.discovery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.SeedHostsProvider;
import org.elasticsearch.discovery.consul.ConsulUnicastHostsProvider;
import org.elasticsearch.plugins.DiscoveryPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.TransportService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ConsulDiscoveryPlugin extends Plugin implements DiscoveryPlugin {
    private static Logger logger = LogManager.getLogger(ConsulDiscoveryPlugin.class);

    private final Settings settings;

    public ConsulDiscoveryPlugin(Settings settings) {
        this.settings = settings;
        logger.info("Starting Consul discovery plugin");
    }

    @Override
    public Map<String, Supplier<SeedHostsProvider>> getSeedHostProviders(TransportService transportService,
                                                                            NetworkService networkService) {
        return Collections.singletonMap(
                "consul",
                () -> {
                    return new ConsulUnicastHostsProvider(settings, transportService);
                });
    }

    @Override
    public List<Setting<?>> getSettings() {
        return Arrays.asList(
                ConsulUnicastHostsProvider.CONSUL_LOCALWSPORT,
                ConsulUnicastHostsProvider.CONSUL_LOCALWSHOST,
                ConsulUnicastHostsProvider.CONSUL_SERVICENAMES,
                ConsulUnicastHostsProvider.CONSUL_TAG,
                ConsulUnicastHostsProvider.CONSUL_HEALTHY
        );
    }
}
