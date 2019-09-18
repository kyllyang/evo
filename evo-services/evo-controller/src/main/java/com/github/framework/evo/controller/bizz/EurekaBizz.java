package com.github.framework.evo.controller.bizz;

import com.github.framework.evo.controller.api.EurekaApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2019-09-18 14:47
 */
@Slf4j
@Service
public class EurekaBizz {
	@Autowired
	private EurekaApi eurekaApi;

	public void apps() {
		log.info(eurekaApi.apps());
	}
}
