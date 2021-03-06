package com.github.framework.evo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * User: Kyll
 * Date: 2018-05-17 15:21
 */
@EnableCircuitBreaker
@SpringBootApplication
public class EvoCommunicationApplication {
	public static void main(String[] args) {
		SpringApplication.run(EvoCommunicationApplication.class, args);
	}
}
