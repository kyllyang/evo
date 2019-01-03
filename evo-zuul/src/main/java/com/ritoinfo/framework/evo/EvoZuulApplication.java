package com.ritoinfo.framework.evo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * User: Kyll
 * Date: 2018-02-26 20:53
 */
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.ritoinfo.framework.evo.zuul.routelocator.dao")
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class EvoZuulApplication {
	public static void main(String[] args) {
		SpringApplication.run(EvoZuulApplication.class, args);
	}
}
