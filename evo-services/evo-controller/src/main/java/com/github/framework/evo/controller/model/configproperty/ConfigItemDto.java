package com.github.framework.evo.controller.model.configproperty;

import com.github.framework.evo.common.validate.group.CheckGroup;
import com.github.framework.evo.common.validate.group.CreateGroup;
import com.github.framework.evo.common.validate.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2019-05-24 13:43
 */
@Data
public class ConfigItemDto {
	private String label;
	private String application;
	@NotBlank(groups = {CheckGroup.class, CreateGroup.class, UpdateGroup.class})
	private String key;
	private Map<String, String> valueMap;
	private Map<String, String> idMap;
	private String comment;
	private Long[] ids;
	private String[] values;
}
