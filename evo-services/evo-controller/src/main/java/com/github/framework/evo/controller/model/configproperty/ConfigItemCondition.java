package com.github.framework.evo.controller.model.configproperty;

import com.github.framework.evo.common.model.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: Kyll
 * Date: 2019-05-24 14:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigItemCondition extends PageDto {
	private Long id;
	private Long[] ids;
	private String label;
	private String application;
	private String[] profiles;
	private String profile;
	private String key;
	private String value;
	private String comment;
}
