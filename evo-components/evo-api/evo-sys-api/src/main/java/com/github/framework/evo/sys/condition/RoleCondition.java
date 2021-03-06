package com.github.framework.evo.sys.condition;

import com.github.framework.evo.common.model.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: Kyll
 * Date: 2018-03-04 17:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleCondition extends PageDto {
	private Long id;
	private String name;
	private String code;
}
