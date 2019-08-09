package com.github.framework.evo.auth.rest;

import com.github.framework.evo.auth.bizz.LogoutBizz;
import com.github.framework.evo.common.uitl.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Kyll
 * Date: 2019-01-16 13:45
 */
@Slf4j
@RestController
public class LogoutRest {
	@Autowired
	private LogoutBizz logoutBizz;

	@PostMapping("/logout")
	public void logout(HttpServletRequest request) {
		logoutBizz.logout(HttpServletUtil.extractAccessToken(request));
	}
}
