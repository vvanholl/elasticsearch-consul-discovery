Consul Based Discovery Plugin for Elasticsearch
======================================

Uses [Consul](https://consul.io) API for Elasticsearch discovery

## Installation

Do `vagrant up`, `vagrant ssh` and then run `mvn package` to build.

The plugin ZIP will be under target/releases/

Do

```
bin/plugin --url file:///path-to-plugin-zip --install lithiumtech/elasticsearch-consul-discovery
```

to install plugin.

For easy provisioning, an RPM file is created under target/rpm if you do `mvn package rpm:rpm`. The plugin ZIP will be under /usr/local/lib when the RPM has been installed.

## Configuration

```
discovery:
  type: consul
  consul:
    service-names: ["CONSUL_SERVICE_NAME1", "CONSUL_SERVICE_NAME2"]
    healthy: true
    tag:  OPTIONAL_TAG_TO_FILTER_NODES
    local-ws-port:  OPTIONAL_TO_SPECIFY_LOCAL_HTTP_API_PORT_FOR_CONSUL_DEFAULT_IS_8500
```


Key|Example|Description
---|---|---
`discovery.consul.service-names`|`es-dc01-teamA-cluster01-data-nodes`| an array of service names those are registered in consul
`discovery.consul.healthy`|`true`| boolean to determine if we should only discover passing/healthy services (default: true)
`discovery.consul.tag`|`searcher`| **Optional** parameter `tag` to filter nodes registered for given service names, [read more..](https://www.consul.io/docs/agent/services.html)
`discovery.consul.local-ws-port`|`8500`|**Optional** parameter to specify the rest web end point's port on localhost for consul. **default** value is `8500` [read more...] (https://www.consul.io/docs/agent/options.html#http_port)


This plugin is based on https://github.com/grantr/elasticsearch-srv-discovery and
modified to be based on consul API calls instead of DNS records.
