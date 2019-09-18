package com.github.framework.evo.controller.model.eureka;

import lombok.Data;

/**
 * User: Kyll
 * Date: 2019-09-18 14:41
 */
@Data
public class ApplicationDto {
	private String name;
	private InstanceDto[] instance;
}
