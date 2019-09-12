package com.github.framework.evo.communication.rest;

import com.github.framework.evo.communication.api.MessageApi;
import com.github.framework.evo.communication.bizz.MessageBizz;
import com.github.framework.evo.communication.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Kyll
 * Date: 2019-09-12 08:26
 */
@Slf4j
@RestController("/message")
public class MessageRest implements MessageApi {
	@Autowired
	private MessageBizz messageBizz;

	@Override
	public Integer countUnread() {
		return 0;
	}

	@Override
	public void send(MessageDto messageDto) {
		messageBizz.send(messageDto);
	}
}
