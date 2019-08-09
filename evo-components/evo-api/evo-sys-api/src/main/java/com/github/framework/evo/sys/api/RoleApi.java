package com.github.framework.evo.sys.api;

import com.github.framework.evo.sys.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * User: Kyll
 * Date: 2018-03-04 18:06
 */
@FeignClient(value = "evo-sys", path = "/role")
public interface RoleApi {
	@PostMapping("/codes")
	List<RoleDto> findByCodes(@RequestBody String[] codes);

	@GetMapping("/user/id/{userId}")
	List<RoleDto> findByUserId(@PathVariable("userId") Long userId);

	@GetMapping("/user/username/{username}")
	List<RoleDto> findByUsername(@PathVariable("username") String username);
}
