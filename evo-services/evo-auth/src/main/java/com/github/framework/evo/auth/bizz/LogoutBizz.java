package com.github.framework.evo.auth.bizz;

import com.github.framework.evo.auth.assist.JwtAssist;
import com.github.framework.evo.auth.assist.RedisAssist;
import com.github.framework.evo.auth.assist.VerifyResult;
import com.github.framework.evo.common.Const;
import com.github.framework.evo.common.SR;
import com.github.framework.evo.common.exception.BusinessException;
import com.github.framework.evo.data.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2019-01-16 15:09
 */
@Slf4j
@Service
public class LogoutBizz {
	@Autowired
	private JwtAssist jwtAssist;
	@Autowired
	private RedisAssist redisAssist;
	@Autowired
	private RedisService redisService;

	public void logout(String accessToken) {
		log.info("accessToken: {}", accessToken);
		VerifyResult verifyResult = jwtAssist.check(accessToken);
		log.info("verifyResult: {}", verifyResult);
		if (VerifyResult.SUCCESS != verifyResult
				|| !redisService.delete(redisAssist.generate(Const.BF_ACCESS_TOKEN, accessToken))
				|| !redisService.delete(redisAssist.generate(Const.BF_REFRESH_TOKEN, accessToken))) {
			throw new BusinessException(SR.RC.AUTH_LOGOUT, accessToken);
		}
	}
}
