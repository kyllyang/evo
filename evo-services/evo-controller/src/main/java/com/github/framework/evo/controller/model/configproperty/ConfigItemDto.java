package com.github.framework.evo.controller.model.configproperty;

import lombok.Data;

import java.util.Map;

/**
 * User: Kyll
 * Date: 2019-05-24 13:43
 */
@Data
public class ConfigItemDto {
	private String label;
	private String application;
	private String key;
	private Map<String, String> valueMap;
	private Map<String, String> idMap;
	private String comment;
}
