package com.github.framework.evo.controller.rest;

import com.github.framework.evo.controller.bizz.EurekaBizz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Kyll
 * Date: 2019-09-18 14:48
 */
@Slf4j
@RequestMapping("/eureka")
@RestController
public class EurekaRest {
	@Autowired
	private EurekaBizz eurekaBizz;

	@GetMapping("/services")
	public void listServices() {
		eurekaBizz.apps();
	}

	@PutMapping("/online/{serviceId}/{instanceId}")
	public void online(@PathVariable String serviceId, @PathVariable String instanceId) {
		eurekaBizz.online(serviceId, instanceId);
	}

	@DeleteMapping("/offline/{serviceId}/{instanceId}")
	public void offline(@PathVariable String serviceId, @PathVariable String instanceId) {
		eurekaBizz.offline(serviceId, instanceId);
	}
}
