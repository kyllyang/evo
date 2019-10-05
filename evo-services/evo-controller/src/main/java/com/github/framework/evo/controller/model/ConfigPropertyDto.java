package com.github.framework.evo.controller.model;

import lombok.Data;

/**
 * User: Kyll
 * Date: 2019-06-09 02:33
 */
@Data
public class ConfigPropertyDto {
	private Long id;
	private String label;
	private String application;
	private String profile;
	private String key;
	private String value;
	private String comment;
}
