Consul Based Discovery Plugin for Elasticsearch
======================================

Uses [Consul](https://consul.io) API for Elasticsearch discovery


## Configuration

```
discovery:
  type: consul
  consul:
    service-name: CONSUL_SERVICE_NAME
    tag:  OPTIONAL_TAG_TO_FILTER_NODES
    local-ws-port:  OPTIONAL_TO_SPECIFY_LOCAL_HTTP_API_PORT_FOR_CONSUL_DEFAULT_IS_8500


```
