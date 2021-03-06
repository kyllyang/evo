package com.github.framework.evo.sys.condition;

import com.github.framework.evo.common.model.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: Kyll
 * Date: 2018-02-13 13:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserCondition extends PageDto {
	private Long id;
	private String username;
	private String password;
	private String name;
	private String code;
	private String email;
	private String mobileNumber;
	private String freeze;
	private String loginTime;
	private String loginIp;
	private String lastLoginTime;
	private String lastLoginIp;
}
