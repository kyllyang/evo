package com.github.framework.evo.autoconfigure.controller;

import com.github.framework.evo.common.exception.HttpInvokeException;
import com.github.framework.evo.common.uitl.IoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;

/**
 * User: Kyll
 * Date: 2019-05-23 10:27
 */
@Configuration
@EnableConfigurationProperties(ControllerProperties.class)
public class ControllerConfiguration {
	private final ControllerProperties controllerProperties;

	@Autowired
	public ControllerConfiguration(ControllerProperties controllerProperties) {
		this.controllerProperties = controllerProperties;
	}

	@ConditionalOnMissingBean(RestTemplate.class)
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
				return clientHttpResponse.getStatusCode().isError();
			}

			@Override
			public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
				HttpStatus httpStatus = clientHttpResponse.getStatusCode();
				if (httpStatus.isError()) {
					throw new HttpInvokeException(clientHttpResponse.getStatusCode().value(), clientHttpResponse.getStatusText(), clientHttpResponse.getHeaders(), IoUtil.toString(clientHttpResponse.getBody()));
				}
			}
		});
		return restTemplate;
	}

	@Bean
	public WebMvcConfigurationSupport getConfigurer() {
		return new WebMvcConfigurationSupport() {
			@Override
			protected void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true)
						.exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(18000L);
			}
		};
	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(18000L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
}
