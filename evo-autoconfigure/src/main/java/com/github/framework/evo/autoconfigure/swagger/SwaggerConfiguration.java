package com.github.framework.evo.autoconfigure.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * User: Kyll
 * Date: 2018-05-15 21:47
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("(?:(?!^/error|/actuator).)*")) // 排除 basic-error-controller, operation-handler, web-mvc-endpoint-handler-mapping
				.build();
	}
}
