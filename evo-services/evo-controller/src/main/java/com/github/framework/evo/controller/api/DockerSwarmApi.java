package com.github.framework.evo.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User: Kyll
 * Date: 2019-06-13 16:47
 */
@Slf4j
@Component
public class DockerSwarmApi extends RestTemplateApi {
	private String url;
	private static final String INSPECT_PATH = "/swarm";
	private static final String LIST_NODES_PATH = "/nodes";
	private static final String INSPECT_NODE_PATH = "/nodes/{id}";
	private static final String UPDATE_NODE_PATH = "/nodes/{id}/update?version={version}";
	private static final String DELETE_NODE_PATH = "/nodes/{id}?force={force}";
	private static final String LIST_SERVICES_PATH = "/services";
	private static final String INSPECT_SERVICE_PATH = "/services/{id}";
	private static final String UPDATE_SERVICE_PATH = "/services/{id}/update?version={version}";
	private static final String DELETE_SERVICE_PATH = "/services/{id}";
	private static final String LIST_TASKS_PATH = "/tasks";

	@PostConstruct
	public void init() {
		url = "http://" + controllerProperties.getDockerSwarm().getHost() + ":" +  controllerProperties.getDockerSwarm().getPort();
	}

	public String inspect() {
		return exchange(url + INSPECT_PATH, HttpMethod.GET);
	}

	public String listNodes() {
		return exchange(url + LIST_NODES_PATH, HttpMethod.GET);
	}

	public String inspectNode(String id) {
		return exchange(url + INSPECT_NODE_PATH.replace("{id}", id), HttpMethod.GET);
	}

	public String updateNode(String id, String version, JsonNode jsonNode) {
		return exchange(url + UPDATE_NODE_PATH.replace("{id}", id).replace("{version}", version), HttpMethod.POST, jsonNode.toString());
	}

	public String deleteNode(String id, String force) {
		return exchange(url + DELETE_NODE_PATH.replace("{id}", id).replace("{force}", force), HttpMethod.DELETE);
	}

	public String listServices() {
		return exchange(url + LIST_SERVICES_PATH, HttpMethod.GET);
	}

	public String inspectService(String id) {
		return exchange(url + INSPECT_SERVICE_PATH.replace("{id}", id), HttpMethod.GET);
	}

	public String updateService(String id, String version, JsonNode jsonNode) {
		return exchange(url + UPDATE_SERVICE_PATH.replace("{id}", id).replace("{version}", version), HttpMethod.POST, jsonNode.toString());
	}

	public String deleteService(String id) {
		return exchange(url + DELETE_SERVICE_PATH.replace("{id}", id), HttpMethod.DELETE);
	}

	public String listTasks() {
		return exchange(url + LIST_TASKS_PATH, HttpMethod.GET);
	}
}
