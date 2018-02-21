Consul Based Discovery Plugin for Elasticsearch
===============================================

Uses [Consul](https://consul.io) API for Elasticsearch discovery

## Build

Run `gradle build` to build plugin.

The plugin zip archive will be under build/distributions/

## Installation

```
./bin/elasticsearch-plugin install -b https://distfiles.compuscene.net/elasticsearch/elasticsearch-consul-discovery-5.5.2.0.zip
```

**Do not forget to restart the node after installation !**

## Configuration

```
discovery:
  discovery.zen.hosts_provider: consul
  consul:
    service-names: ["CONSUL_SERVICE_NAME1", "CONSUL_SERVICE_NAME2"]
    healthy: true
    tag:  OPTIONAL_TAG_TO_FILTER_NODES
    local-ws-port:  OPTIONAL_TO_SPECIFY_LOCAL_HTTP_API_PORT_FOR_CONSUL_DEFAULT_IS_8500
    local-ws-host:  OPTIONAL_TO_SPECIFY_LOCAL_HTTP_API_HOST_FOR_CONSUL_DEFAULT_IS_localhost
```


Key|Example|Description
---|---|---
`discovery.consul.service-names`|`es-dc01-teamA-cluster01-data-nodes`| an array of service names those are registered in consul
`discovery.consul.healthy`|`true`| boolean to determine if we should only discover passing/healthy services (default: true)
`discovery.consul.tag`|`searcher`| **Optional** parameter `tag` to filter nodes registered for given service names, [read more..](https://www.consul.io/docs/agent/services.html)
`discovery.consul.local-ws-host`|`http://localhost`|**Optional** parameter to specify the rest web end point's host for consul with schema. **default** value is `http://localhost`
`discovery.consul.local-ws-port`|`8500`|**Optional** parameter to specify the rest web end point's port for consul. **default** value is `8500` [read more...] (https://www.consul.io/docs/agent/options.html#http_port)


This plugin is based on https://github.com/grantr/elasticsearch-srv-discovery and
modified to be based on consul API calls instead of DNS records.
