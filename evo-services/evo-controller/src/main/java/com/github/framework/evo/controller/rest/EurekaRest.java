package com.github.framework.evo.controller.rest;

import com.github.framework.evo.controller.bizz.EurekaBizz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/apps")
	public void listNodes() {
		eurekaBizz.apps();
	}
}
