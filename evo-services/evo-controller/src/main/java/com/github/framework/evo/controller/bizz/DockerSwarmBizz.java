package com.github.framework.evo.controller.bizz;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.framework.evo.autoconfigure.controller.ControllerProperties;
import com.github.framework.evo.common.SR;
import com.github.framework.evo.common.exception.BusinessException;
import com.github.framework.evo.common.exception.HttpInvokeException;
import com.github.framework.evo.common.uitl.JsonUtil;
import com.github.framework.evo.controller.api.DockerSwarmApi;
import com.github.framework.evo.controller.model.dockerswarm.ManagerStatusDto;
import com.github.framework.evo.controller.model.dockerswarm.MountDto;
import com.github.framework.evo.controller.model.dockerswarm.NodeDto;
import com.github.framework.evo.controller.model.dockerswarm.PortDto;
import com.github.framework.evo.controller.model.dockerswarm.ServiceDto;
import com.github.framework.evo.controller.model.dockerswarm.SwarmDto;
import com.github.framework.evo.controller.model.dockerswarm.TaskDto;
import com.github.framework.evo.controller.model.dockerswarm.VirtualIPDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * User: Kyll
 * Date: 2019-06-14 08:57
 */
@Slf4j
@Service
public class DockerSwarmBizz {
	@Autowired
	private ControllerProperties controllerProperties;
	@Autowired
	private DockerSwarmApi dockerSwarmApi;

	public SwarmDto inspectSwarm() {
		JsonNode jsonNode = JsonUtil.jsonToNode(dockerSwarmApi.inspect());

		SwarmDto swarmDto = new SwarmDto();
		swarmDto.setId(jsonNode.get("ID").textValue());
		swarmDto.setCreatedAt(jsonNode.get("CreatedAt").textValue());
		swarmDto.setUpdatedAt(jsonNode.get("UpdatedAt").textValue());
		return swarmDto;
	}

	public List<NodeDto> listNodes() {
		JsonNode jsonNode = JsonUtil.jsonToNode(dockerSwarmApi.listNodes());

		List<NodeDto> nodeDtoList = new ArrayList<>();
		jsonNode.forEach(nodeJsonNode -> nodeDtoList.add(toNodeDto(nodeJsonNode)));

		nodeDtoList.sort((o1, o2) -> {
			if (o1.getAddr().equals(o2.getAddr())) {
				return 0;
			}

			Set<String> set = new TreeSet<>();
			set.add(o1.getAddr());
			set.add(o2.getAddr());
			return set.toArray(new String[0])[0].equals(o1.getAddr()) ? -1 : 1;
		});

		return nodeDtoList;
	}

	public NodeDto inspectNode(String id) {
		return toNodeDto(JsonUtil.jsonToNode(dockerSwarmApi.inspectNode(id)));
	}

	public void updateNode(NodeDto nodeDto) {
		ObjectNode objectNode = JsonUtil.createObjectNode();
		objectNode.put("Role", nodeDto.getRole());
		objectNode.put("Availability", nodeDto.getAvailability());

		dockerSwarmApi.updateNode(nodeDto.getId(), nodeDto.getVersionIndex().toString(), objectNode);
	}

	public void deleteNode(String id, boolean force) {
		try {
			dockerSwarmApi.deleteNode(id, Boolean.valueOf(force).toString());
		} catch (HttpInvokeException e) {
			throw new BusinessException(SR.RC.CONTROLLER_DOCKER_SWARM_NODES, e, e.getEntity().getBody());
		}
	}

	public List<ServiceDto> listServices() {
		JsonNode jsonNode = JsonUtil.jsonToNode(dockerSwarmApi.listServices());

		List<ServiceDto> serviceDtoList = new ArrayList<>();
		jsonNode.forEach(serviceJsonNode -> serviceDtoList.add(toServiceDto(serviceJsonNode)));

		serviceDtoList.sort((o1, o2) -> {
			if (o1.getName().equals(o2.getName())) {
				return 0;
			}

			Set<String> set = new TreeSet<>();
			set.add(o1.getName());
			set.add(o2.getName());
			return set.toArray(new String[0])[0].equals(o1.getName()) ? -1 : 1;
		});

		return serviceDtoList;
	}

	public ServiceDto inspectService(String id) {
		return toServiceDto(JsonUtil.jsonToNode(dockerSwarmApi.inspectService(id)));
	}

	public void updateService(String id, int replicas) {
		ServiceDto serviceDto = inspectService(id);

		ObjectNode serviceNode = JsonUtil.createObjectNode();
		serviceNode.put("Name", serviceDto.getName());
		ObjectNode containerSpecNode = serviceNode.putObject("TaskTemplate").putObject("ContainerSpec");
		containerSpecNode.put("Image", serviceDto.getImage());
		ArrayNode mountsNode = containerSpecNode.putArray("Mounts");
		serviceDto.getMountDtoList().forEach(mountDto -> {
			ObjectNode mountNode = mountsNode.addObject();
			mountNode.put("Source", mountDto.getSource());
			mountNode.put("Target", mountDto.getTarget());
			mountNode.put("Type", mountDto.getType());
		});
		ObjectNode replicatedNode = serviceNode.putObject("Mode").putObject("Replicated");
		replicatedNode.put("Replicas", replicas);
		ArrayNode networksNode = serviceNode.putArray("Networks");
		ObjectNode networkNode = networksNode.addObject();
		networkNode.put("Target", controllerProperties.getDockerSwarm().getNetwork().getName());
		ArrayNode portsNode = serviceNode.putObject("EndpointSpec").putArray("Ports");
		serviceDto.getPortList().forEach(portDto -> {
			ObjectNode portNode = portsNode.addObject();
			portNode.put("Protocol", portDto.getProtocol());
			portNode.put("PublishedPort", portDto.getPublishedPort());
			portNode.put("TargetPort", portDto.getTargetPort());
		});

		dockerSwarmApi.updateService(serviceDto.getId(), serviceDto.getVersionIndex().toString(), serviceNode);
	}

	public List<TaskDto> listTasks() {
		JsonNode jsonNode = JsonUtil.jsonToNode(dockerSwarmApi.listTasks());

		List<TaskDto> taskDtoList = new ArrayList<>();
		jsonNode.forEach(taskJsonNode -> taskDtoList.add(toTaskDto(taskJsonNode)));

		taskDtoList.sort((o1, o2) -> {
			if (o1.getImage().equals(o2.getImage())) {
				return 0;
			}

			Set<String> set = new TreeSet<>();
			set.add(o1.getImage());
			set.add(o2.getImage());
			return set.toArray(new String[0])[0].equals(o1.getImage()) ? -1 : 1;
		});

		Map<String, ServiceDto> serviceDtoMap = new HashMap<>();
		Map<String, NodeDto> nodeDtoMap = new HashMap<>();

		CountDownLatch countDownLatch = new CountDownLatch(taskDtoList.size() * 2);
		for (TaskDto taskDto : taskDtoList) {
			new Thread(() -> {
				String serviceId = taskDto.getServiceId();
				taskDto.setService(serviceDtoMap.computeIfAbsent(serviceId, k -> inspectService(serviceId)));
				countDownLatch.countDown();
			}).start();

			new Thread(() -> {
				String nodeId = taskDto.getNodeId();
				taskDto.setNode(nodeDtoMap.computeIfAbsent(nodeId, k -> inspectNode(nodeId)));
				countDownLatch.countDown();
			}).start();
		}

		try {
			if (!countDownLatch.await(3000, TimeUnit.MILLISECONDS)) {
				log.warn("inspectService or inspectNode is timeout");
			}
		} catch (InterruptedException e) {
			throw new BusinessException(SR.RC.CONTROLLER_DOCKER_SWARM_TASKS, e);
		}

		return taskDtoList;
	}

	private NodeDto toNodeDto(JsonNode nodeJsonNode) {
		NodeDto nodeDto = new NodeDto();

		nodeDto.setId(nodeJsonNode.get("ID").textValue());
		nodeDto.setVersionIndex(nodeJsonNode.get("Version").get("Index").intValue());
		nodeDto.setCreatedAt(nodeJsonNode.get("CreatedAt").textValue());
		nodeDto.setUpdatedAt(nodeJsonNode.get("UpdatedAt").textValue());

		JsonNode specJsonNode = nodeJsonNode.get("Spec");
		nodeDto.setRole(specJsonNode.get("Role").textValue());
		nodeDto.setAvailability(specJsonNode.get("Availability").textValue());

		JsonNode descriptionJsonNode = nodeJsonNode.get("Description");
		nodeDto.setHostname(descriptionJsonNode.get("Hostname").textValue());

		JsonNode platformJsonNode = descriptionJsonNode.get("Platform");
		nodeDto.setArchitecture(platformJsonNode.get("Architecture").textValue());
		nodeDto.setOs(platformJsonNode.get("OS").textValue());

		JsonNode resourcesJsonNode = descriptionJsonNode.get("Resources");
		nodeDto.setNanoCPUs(resourcesJsonNode.get("NanoCPUs").longValue());
		nodeDto.setMemoryBytes(resourcesJsonNode.get("MemoryBytes").longValue());

		JsonNode engineJsonNode = descriptionJsonNode.get("Engine");
		nodeDto.setEngineVersion(engineJsonNode.get("EngineVersion").textValue());

		JsonNode statusJsonNode = nodeJsonNode.get("Status");
		nodeDto.setState(statusJsonNode.get("State").textValue());
		nodeDto.setAddr(statusJsonNode.get("Addr").textValue());

		if (nodeJsonNode.has("ManagerStatus")) {
			JsonNode managerStatusJsonNode = nodeJsonNode.get("ManagerStatus");

			ManagerStatusDto managerStatusDto = new ManagerStatusDto();
			managerStatusDto.setLeader(managerStatusJsonNode.get("Leader").booleanValue());
			managerStatusDto.setReachability(managerStatusJsonNode.get("Reachability").textValue());
			managerStatusDto.setAddr(managerStatusJsonNode.get("Addr").textValue());
			nodeDto.setManagerStatus(managerStatusDto);
		}

		return nodeDto;
	}

	private ServiceDto toServiceDto(JsonNode serviceJsonNode) {
		ServiceDto serviceDto = new ServiceDto();

		serviceDto.setId(serviceJsonNode.get("ID").textValue());
		serviceDto.setVersionIndex(serviceJsonNode.get("Version").get("Index").intValue());
		serviceDto.setCreatedAt(serviceJsonNode.get("CreatedAt").textValue());
		serviceDto.setUpdatedAt(serviceJsonNode.get("UpdatedAt").textValue());

		JsonNode specJsonNode = serviceJsonNode.get("Spec");
		serviceDto.setName(specJsonNode.get("Name").textValue());

		JsonNode containerSpecJsonNode = specJsonNode.get("TaskTemplate").get("ContainerSpec");
		serviceDto.setImage(containerSpecJsonNode.get("Image").textValue());

		List<MountDto> mountDtoList = new ArrayList<>();
		if (containerSpecJsonNode.has("Mounts")) {
			containerSpecJsonNode.get("Mounts").forEach(mountJsonNode -> mountDtoList.add(toMountDto(mountJsonNode)));
		}
		serviceDto.setMountDtoList(mountDtoList);

		JsonNode modeJsonNode = specJsonNode.get("Mode");
		if (modeJsonNode.has("Replicated")) {
			serviceDto.setMode("Replicated");
			serviceDto.setReplicas(modeJsonNode.get("Replicated").get("Replicas").intValue());
		} else if (modeJsonNode.has("Global")) {
			serviceDto.setMode("Global");
		}

		JsonNode endpointJsonNode = serviceJsonNode.get("Endpoint");
		if (endpointJsonNode.has("Ports")) {
			List<PortDto> portDtoList = new ArrayList<>();
			endpointJsonNode.get("Ports").forEach(portJsonNode -> portDtoList.add(toPortDto(portJsonNode)));
			serviceDto.setPortList(portDtoList);
		}
		if (endpointJsonNode.has("VirtualIPs")) {
			List<VirtualIPDto> virtualIPDtoList = new ArrayList<>();
			endpointJsonNode.get("VirtualIPs").forEach(virtualIPJsonNode -> virtualIPDtoList.add(toVirtualIPDto(virtualIPJsonNode)));
			serviceDto.setVirtualIPList(virtualIPDtoList);
		}

		return serviceDto;
	}

	private MountDto toMountDto(JsonNode mountJsonNode) {
		MountDto mountDto = new MountDto();

		mountDto.setType(mountJsonNode.get("Type").textValue());
		mountDto.setSource(mountJsonNode.get("Source").textValue());
		mountDto.setTarget(mountJsonNode.get("Target").textValue());

		return mountDto;
	}

	private PortDto toPortDto(JsonNode portJsonNode) {
		PortDto portDto = new PortDto();

		portDto.setProtocol(portJsonNode.get("Protocol").textValue());
		portDto.setTargetPort(portJsonNode.get("TargetPort").intValue());
		portDto.setPublishedPort(portJsonNode.get("PublishedPort").intValue());
		portDto.setPublishMode(portJsonNode.get("PublishMode").textValue());

		return portDto;
	}

	private VirtualIPDto toVirtualIPDto(JsonNode virtualIPJsonNode) {
		VirtualIPDto virtualIPDto = new VirtualIPDto();

		virtualIPDto.setNetworkID(virtualIPJsonNode.get("NetworkID").textValue());
		virtualIPDto.setAddr(virtualIPJsonNode.get("Addr").textValue());

		return virtualIPDto;
	}

	private TaskDto toTaskDto(JsonNode taskJsonNode) {
		TaskDto taskDto = new TaskDto();

		taskDto.setId(taskJsonNode.get("ID").textValue());
		taskDto.setVersionIndex(taskJsonNode.get("Version").get("Index").intValue());
		taskDto.setCreatedAt(taskJsonNode.get("CreatedAt").textValue());
		taskDto.setUpdatedAt(taskJsonNode.get("UpdatedAt").textValue());

		JsonNode specJsonNode = taskJsonNode.get("Spec");

		JsonNode containerSpecJsonNode = specJsonNode.get("ContainerSpec");
		taskDto.setImage(containerSpecJsonNode.get("Image").textValue());

		taskDto.setServiceId(taskJsonNode.get("ServiceID").textValue());
		taskDto.setNodeId(taskJsonNode.get("NodeID").textValue());

		JsonNode statusJsonNode = taskJsonNode.get("Status");
		taskDto.setState(statusJsonNode.get("State").textValue());
		taskDto.setMessage(statusJsonNode.get("Message").textValue());

		if (statusJsonNode.has("ContainerStatus")) {
			JsonNode containerStatusJsonNode = statusJsonNode.get("ContainerStatus");
			taskDto.setContainerId(containerStatusJsonNode.get("ContainerID").textValue());
			taskDto.setPid(containerStatusJsonNode.get("PID").intValue());
		}

		taskDto.setDesiredState(taskJsonNode.get("DesiredState").textValue());

		return taskDto;
	}
}
