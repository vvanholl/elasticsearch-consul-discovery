/*
These files were modified by Lithium Technologies, Inc. and all such modifications are:
Copyright © 2015 Lithium Technologies, Inc. All rights reserved subject to the terms of the MIT License below.
Original files from the “Elasticsearch SRV discovery plugin” project, prior to modification by Lithium, are:

Copyright (c) 2015 Grant Rodgers

MIT License
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package org.elasticsearch.discovery.consul;

import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.cluster.ClusterService;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.DiscoverySettings;
import org.elasticsearch.discovery.zen.ZenDiscovery;
import org.elasticsearch.discovery.zen.elect.ElectMasterService;
import org.elasticsearch.discovery.zen.ping.ZenPingService;
import org.elasticsearch.node.settings.NodeSettingsService;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

public class ConsulDiscovery extends ZenDiscovery {

	public static final String CONSUL = "consul";

	@Inject

	public ConsulDiscovery(Settings settings, ClusterName clusterName, ThreadPool threadPool,
	                       TransportService transportService, final ClusterService clusterService,
	                       NodeSettingsService nodeSettingsService, ZenPingService pingService,
	                       ElectMasterService electMasterService, DiscoverySettings discoverySettings) {
		super(settings, clusterName, threadPool, transportService, clusterService, nodeSettingsService,
		      pingService, electMasterService, discoverySettings);
	}
}
