package com.github.framework.evo.communication.api;

import com.github.framework.evo.communication.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User: Kyll
 * Date: 2019-09-12 08:29
 */
@FeignClient(value = "evo-communication", path = "/message")
public interface MessageApi {
	@PostMapping("/send")
	void send(@RequestBody MessageDto messageDto);
}
