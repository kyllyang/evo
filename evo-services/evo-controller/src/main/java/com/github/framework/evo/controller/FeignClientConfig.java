package com.github.framework.evo.controller;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Kyll
 * Date: 2019-09-20 11:35
 */
@Configuration
public class FeignClientConfig {
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
