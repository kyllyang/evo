package com.ritoinfo.framework.evo.oauth2.extend.mnvc;

import com.ritoinfo.framework.evo.auth.api.IamApi;
import com.ritoinfo.framework.evo.auth.model.UserDetailsDto;
import com.ritoinfo.framework.evo.oauth2.extend.LoginUser;
import com.ritoinfo.framework.evo.oauth2.extend.LoginUserDetails;
import com.ritoinfo.framework.evo.oauth2.extend.LoginUserDetailsService;
import com.ritoinfo.framework.evo.oauth2.extend.exception.MobileNumberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * User: Kyll
 * Date: 2018-12-20 11:31
 */
@Component
public class MobileNumberVerifyCodeUserDetailsServiceImpl implements LoginUserDetailsService {
	@Autowired
	private IamApi iamApi;

	@Override
	public LoginUserDetails loadUserByMobileNumber(String mobileNumber) {
		UserDetailsDto userDetailsDto = iamApi.getByMobileNumber(mobileNumber).getData();

		if (null == userDetailsDto) {
			throw new MobileNumberNotFoundException(mobileNumber);
		}

		return LoginUser.builder()
				.id(userDetailsDto.getId())
				.name(userDetailsDto.getName())
				.code(userDetailsDto.getCode())
				.username(userDetailsDto.getUsername())
				.email(userDetailsDto.getEmail())
				.mobileNumber(userDetailsDto.getMobileNumber())
				.authorities(Collections.emptySet())
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true).build();
	}
}
