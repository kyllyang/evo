package com.ritoinfo.framework.evo.sp.auth.rest;

import com.ritoinfo.framework.evo.common.Const;
import com.ritoinfo.framework.evo.sp.auth.bizz.AuthBizz;
import com.ritoinfo.framework.evo.sp.auth.dto.LoginDto;
import com.ritoinfo.framework.evo.sp.auth.dto.VerifyDto;
import com.ritoinfo.framework.evo.sp.base.exception.BizzException;
import com.ritoinfo.framework.evo.sp.base.exception.RestException;
import com.ritoinfo.framework.evo.sp.base.model.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Kyll
 * Date: 2018-03-08 14:33
 */
@Slf4j
@RestController
public class AuthRest {
	@Autowired
	private AuthBizz authBizz;

	@PostMapping("login")
	public ServiceResponse<String> login(@Validated @RequestBody LoginDto loginDto) {
		try {
			return ServiceResponse.ok(authBizz.authorize(loginDto));
		} catch (BizzException e) {
			throw new RestException(Const.RC_AUTH_LOGIN);
		}
	}

	@PostMapping("logout")
	public ServiceResponse<Boolean> logout(@RequestBody String token) {
		try {
			return ServiceResponse.ok(authBizz.clear(token));
		} catch (Exception e) {
			throw new RestException(Const.RC_AUTH_LOGOUT);
		}
	}

	@PostMapping("try")
	public ServiceResponse<String> tryRefresh(@RequestBody String token) {
		try {
			return ServiceResponse.ok(authBizz.tryRefresh(token));
		} catch (Exception e) {
			throw new RestException(Const.RC_AUTH_TRY_REFRESH);
		}
	}

	@PostMapping("refresh")
	public ServiceResponse<String> refresh(@RequestBody String token) {
		try {
			return ServiceResponse.ok(authBizz.refresh(token));
		} catch (Exception e) {
			throw new RestException(Const.RC_AUTH_REFRESH, e);
		}
	}

	@PostMapping("verify")
	public ServiceResponse<Boolean> verify(@Validated @RequestBody VerifyDto verifyDto) {
		try {
			return ServiceResponse.ok(authBizz.verify(verifyDto));
		} catch (Exception e) {
			throw new RestException(Const.RC_AUTH_VERIFY);
		}
	}
}
