package com.github.framework.evo.communication.rest;

import com.github.framework.evo.communication.api.MailApi;
import com.github.framework.evo.communication.bizz.MailBizz;
import com.github.framework.evo.communication.dto.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Kyll
 * Date: 2019-09-12 08:32
 */
@Slf4j
@RestController("/mail")
public class MailRest implements MailApi {
	@Autowired
	private MailBizz mailBizz;

	@Override
	public void send(MailDto mailDto) {
		mailBizz.send(mailDto);
	}
}
