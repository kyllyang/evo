package com.github.framework.evo.zuul.loadbalancer;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * User: Kyll
 * Date: 2018-01-10 18:50
 */
@Slf4j
public class BackToSelfRule extends AbstractLoadBalancerRule {
	@Override
	public void initWithNiwsConfig(IClientConfig iClientConfig) {
	}

	@Override
	public Server choose(Object key) {
		return this.choose(this.getLoadBalancer(), key);
	}

	private Server choose(ILoadBalancer loadBalancer, Object key) {
		if (loadBalancer == null) {
			log.warn("No load balancer");
			return null;
		}

		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		String remoteHost = request.getRemoteHost();
		String headerHostPort = request.getHeader("host");

		log.info("Client request: " + request.getRequestURL() + ", addr: " + request.getRemoteAddr() + ", host: " + remoteHost + ", port: " + request.getRemotePort() + ", headerHost: " + headerHostPort);

		Server finalServer = null;
		List<Server> serverList = loadBalancer.getReachableServers();
		if (serverList == null) {
			log.warn("Server list is null");
			return null;
		}

		List<String> hostList = new ArrayList<>();
		for (Server server : serverList) {
			hostList.add(
					server.getHost() + ", " +
					server.getHostPort() + ", " +
					server.getId() + ", " +
					server.getMetaInfo().getAppName() + ", " +
					server.getMetaInfo().getInstanceId() + ", " +
					server.getMetaInfo().getServerGroup() + ", " +
					server.getMetaInfo().getServiceIdForDiscovery() + ", " +
					server.getPort() + ", " +
					server.getScheme() + ", " +
					server.getZone() + ", " +
					server.isAlive() + ", " +
					server.isReadyToServe() + "\r\n"
			);
		}
		log.info("Server list is: " + Arrays.toString(hostList.toArray()));

		for (Server server : serverList) {

			if (server.getHost().equals(remoteHost)) {
				finalServer = server;
				break;
			}
		}

		if (finalServer == null) {
			for (Server server : serverList) {
				if (server.getHost().equals(headerHostPort.split(":")[0])) {
					finalServer = server;
					break;
				}
			}
		}


		if (finalServer == null) {
			int serverCount = serverList.size();
			if (serverCount > 0) {
				finalServer = serverList.get(new Random().nextInt(serverCount));
				log.info("Server on " + remoteHost + " is not exist, use random server: " + finalServer.getHostPort());
			} else {
				log.warn("Server on " + remoteHost + " and eureka server list is not exist");
			}
		} else {
			log.info("Find server on " + remoteHost);
		}

		return finalServer;
	}
}
