package com.github.framework.evo.controller.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User: Kyll
 * Date: 2019-06-09 03:36
 */
@FeignClient(value = "evo-config", path = "/actuator")
public interface ConfigApi {
	@PostMapping("/bus-refresh")
	void busRefresh();

	@PostMapping("/bus-refresh")
	void busRefresh(@RequestParam("destination") String destination);
}
