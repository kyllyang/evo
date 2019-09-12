package com.github.framework.evo.communication.api;

import com.github.framework.evo.communication.dto.SmsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User: Kyll
 * Date: 2018-04-20 16:14
 */
@FeignClient(value = "evo-communication", path = "/sms")
public interface SmsApi {
	@PostMapping("/send")
	void send(@RequestBody SmsDto smsDto);
}
