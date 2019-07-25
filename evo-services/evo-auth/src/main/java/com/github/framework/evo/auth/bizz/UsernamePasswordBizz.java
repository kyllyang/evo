package com.github.framework.evo.auth.bizz;

import com.github.framework.evo.auth.api.IamApi;
import com.github.framework.evo.auth.model.UserDetailsDto;
import com.github.framework.evo.auth.model.UsernamePasswordParam;
import com.github.framework.evo.common.SR;
import com.github.framework.evo.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2019-01-16 11:30
 */
@Slf4j
@Service
public class UsernamePasswordBizz {
	@Autowired
	private IamApi iamApi;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenBizz tokenBizz;

	public String login(UsernamePasswordParam loginDto, String remoteAddr) {
		UserDetailsDto userDetailsDto = iamApi.getByUsername(loginDto.getUsername());
		if (userDetailsDto == null) {
			throw new BusinessException(SR.RC.AUTH_USERNAME_NOT_FOUND, loginDto.getUsername());
		}

		if (passwordEncoder.matches(loginDto.getPassword(), userDetailsDto.getPassword())) {
			iamApi.updateLoginInfo(userDetailsDto.getId(), remoteAddr);
			return tokenBizz.createToken(userDetailsDto);
		}

		throw new BusinessException(SR.RC.AUTH_PASSWORD_INVALID);
	}
}
