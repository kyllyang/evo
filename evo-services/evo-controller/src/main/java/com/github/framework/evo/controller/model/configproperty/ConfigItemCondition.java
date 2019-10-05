package com.github.framework.evo.controller.model.configproperty;

import com.github.framework.evo.common.model.PageDto;
import com.github.framework.evo.common.validate.group.CheckGroup;
import com.github.framework.evo.common.validate.group.CreateGroup;
import com.github.framework.evo.common.validate.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * User: Kyll
 * Date: 2019-05-24 14:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigItemCondition extends PageDto {
	private Long id;
	private String label;
	private String application;
	private String[] profiles;
	@NotBlank(groups = {CheckGroup.class, CreateGroup.class, UpdateGroup.class})
	private String profile;
	@NotBlank(groups = {CheckGroup.class, CreateGroup.class, UpdateGroup.class})
	private String key;
	@NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
	private String value;
	private String comment;
}
