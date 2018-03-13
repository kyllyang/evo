package com.ritoinfo.framework.evo.sp.auth.infa;

import com.ritoinfo.framework.evo.sp.auth.infa.dto.UserDto;
import com.ritoinfo.framework.evo.sp.base.infa.model.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * User: Kyll
 * Date: 2018-03-08 14:59
 */
@FeignClient(value = "evo-sp-sys", path = "/user")
public interface SysUserInfa {
	@GetMapping("/username/{username}")
	ServiceResponse<UserDto> getByUsername(@PathVariable("username") String username);
}