package com.github.framework.evo.controller.bizz;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.framework.evo.common.uitl.JsonUtil;
import com.github.framework.evo.common.uitl.StringUtil;
import com.github.framework.evo.controller.api.EurekaApi;
import com.github.framework.evo.controller.model.eureka.ServiceInstanceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * User: Kyll
 * Date: 2019-09-18 14:47
 */
@Slf4j
@Service
public class EurekaBizz {
	@Autowired
	private EurekaApi eurekaApi;

	public List<ServiceInstanceDto> listInstances() {
		List<ServiceInstanceDto> serviceInstanceDtoList = new ArrayList<>();
		ResponseEntity<byte[]> responseEntity = eurekaApi.apps();
		log.info("object123: " + StringUtil.ungzip(responseEntity.getBody()));

		JsonNode applicationsJN = JsonUtil.jsonToNode(new String(StringUtil.ungzip(responseEntity.getBody()))).get("applications");
		applicationsJN.withArray("application").forEach(applicationJN -> {
			String name = applicationJN.get("name").textValue();

			applicationJN.withArray("instance").forEach(instanceJN -> {
				ServiceInstanceDto serviceInstanceDto = new ServiceInstanceDto();
				serviceInstanceDto.setName(name);
				serviceInstanceDto.setInstanceId(instanceJN.get("instanceId").textValue());
				serviceInstanceDto.setHostName(instanceJN.get("hostName").textValue());
				serviceInstanceDto.setApp(instanceJN.get("app").textValue());
				serviceInstanceDto.setIpAddr(instanceJN.get("ipAddr").textValue());
				serviceInstanceDto.setStatus(instanceJN.get("status").textValue());
				serviceInstanceDto.setOverriddenStatus(instanceJN.get("overriddenStatus").textValue());
				serviceInstanceDto.setPort(instanceJN.get("port").get("$").intValue());
				serviceInstanceDto.setPortEnabled(instanceJN.get("port").get("@enabled").asBoolean());
				serviceInstanceDto.setSecurePort(instanceJN.get("securePort").get("$").intValue());
				serviceInstanceDto.setSecurePortEnabled(instanceJN.get("securePort").get("@enabled").asBoolean());
				serviceInstanceDto.setCountryId(instanceJN.get("countryId").textValue());
				serviceInstanceDto.setRenewalIntervalInSecs(instanceJN.get("leaseInfo").get("renewalIntervalInSecs").intValue());
				serviceInstanceDto.setDurationInSecs(instanceJN.get("leaseInfo").get("durationInSecs").intValue());
				serviceInstanceDto.setRegistrationTimestamp(instanceJN.get("leaseInfo").get("registrationTimestamp").longValue());
				serviceInstanceDto.setLastRenewalTimestamp(instanceJN.get("leaseInfo").get("lastRenewalTimestamp").longValue());
				serviceInstanceDto.setEvictionTimestamp(instanceJN.get("leaseInfo").get("evictionTimestamp").longValue());
				serviceInstanceDto.setServiceUpTimestamp(instanceJN.get("leaseInfo").get("serviceUpTimestamp").longValue());
				serviceInstanceDto.setHomePageUrl(instanceJN.get("homePageUrl").textValue());
				serviceInstanceDto.setStatusPageUrl(instanceJN.get("statusPageUrl").textValue());
				serviceInstanceDto.setHealthCheckUrl(instanceJN.get("healthCheckUrl").textValue());
				serviceInstanceDto.setVipAddress(instanceJN.get("vipAddress").textValue());
				serviceInstanceDto.setSecureVipAddress(instanceJN.get("secureVipAddress").textValue());
				serviceInstanceDto.setCoordinatingDiscoveryServer(instanceJN.get("isCoordinatingDiscoveryServer").booleanValue());
				serviceInstanceDto.setLastUpdatedTimestamp(instanceJN.get("lastUpdatedTimestamp").longValue());
				serviceInstanceDto.setLastDirtyTimestamp(instanceJN.get("lastDirtyTimestamp").longValue());
				serviceInstanceDto.setActionType(instanceJN.get("actionType").textValue());

				Map<String, String> map = new LinkedHashMap<>();
				instanceJN.get("metadata").fieldNames().forEachRemaining(fieldName -> map.put(fieldName, instanceJN.get("metadata").get(fieldName).textValue()));
				serviceInstanceDto.setMetadata(map);

				serviceInstanceDtoList.add(serviceInstanceDto);
			});
		});

		serviceInstanceDtoList.sort((o1, o2) -> {
			if (o1.getApp().equals(o2.getApp())) {
				return 0;
			}

			Set<String> set = new TreeSet<>();
			set.add(o1.getApp());
			set.add(o2.getApp());
			return set.toArray(new String[0])[0].equals(o1.getApp()) ? -1 : 1;
		});

		return serviceInstanceDtoList;
	}

	public void online(String serviceId, String instanceId) {
		eurekaApi.backIntoService(serviceId, instanceId, "UP");
	}

	public void offline(String serviceId, String instanceId) {
		eurekaApi.outOfService(serviceId, instanceId, "OUT_OF_SERVICE");
	}
}
