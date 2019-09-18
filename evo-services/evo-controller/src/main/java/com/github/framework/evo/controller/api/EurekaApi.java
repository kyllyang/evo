package com.github.framework.evo.controller.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * User: Kyll
 * Date: 2019-05-23 10:48
 */
@FeignClient(value = "${evo.controller.eureka.service-id}", path = "/eureka")
public interface EurekaApi {
	@GetMapping("/apps")
	String apps();
}
