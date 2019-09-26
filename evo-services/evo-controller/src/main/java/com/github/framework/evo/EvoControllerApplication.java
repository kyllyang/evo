package com.github.framework.evo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * User: Kyll
 * Date: 2017-11-27 11:57
 */
@MapperScan(basePackages = "com.github.framework.evo.controller.dao")
@EnableSwagger2
@EnableFeignClients(basePackages = "com.github.framework.evo")
@EnableCircuitBreaker
@SpringBootApplication
public class EvoControllerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EvoControllerApplication.class, args);
	}
}
