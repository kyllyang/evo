package com.ritoinfo.framework.evo.sp.sys.rest;

import com.ritoinfo.framework.evo.sp.base.model.ServiceResponse;
import com.ritoinfo.framework.evo.sp.base.starter.rest.BaseRest;
import com.ritoinfo.framework.evo.sp.sys.bizz.FuncBizz;
import com.ritoinfo.framework.evo.sp.sys.condition.FuncCondition;
import com.ritoinfo.framework.evo.sp.sys.dao.FuncDao;
import com.ritoinfo.framework.evo.sp.sys.dto.FuncDto;
import com.ritoinfo.framework.evo.sp.sys.dto.PermissionDto;
import com.ritoinfo.framework.evo.sp.sys.entity.Func;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: Kyll
 * Date: 2018-03-04 18:06
 */
@Slf4j
@RequestMapping("func")
@RestController
public class FuncRest extends BaseRest<FuncBizz, FuncDao, Func, Long, FuncCondition, FuncDto> {
	@GetMapping("/user/username/{username}")
	public ServiceResponse<List<PermissionDto>> username(@PathVariable String username) {
		return ServiceResponse.ok(bizz.findByUsername(username));
	}
}
