package com.github.framework.evo.controller.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User: Kyll
 * Date: 2019-05-23 10:48
 */
@FeignClient(value = "${evo.controller.eureka.service-id}", path = "/eureka")
public interface EurekaApi {
	@GetMapping("/apps")
	ResponseEntity<byte[]> apps();

	@PutMapping("/apps/{serviceId}/{instanceId}/status")
	String outOfService(@PathVariable("serviceId") String serviceId, @PathVariable("instanceId") String instanceId, @RequestParam("value") String value);

	@DeleteMapping("/apps/{serviceId}/{instanceId}/status")
	String backIntoService(@PathVariable("serviceId") String serviceId, @PathVariable("instanceId") String instanceId, @RequestParam("value") String value);
}
