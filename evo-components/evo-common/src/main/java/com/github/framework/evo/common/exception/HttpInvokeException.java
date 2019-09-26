package com.github.framework.evo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2019-09-26 17:27
 */
public class HttpInvokeException extends BaseException {
	@Getter private Integer statusCode;
	@Getter private String statusText;
	@Getter private Entity entity;

	public HttpInvokeException(Integer statusCode, String statusText, Map<String, List<String>> headers, String body) {
		this(statusCode, statusText, headers, body, null);
	}

	public HttpInvokeException(Integer statusCode, String statusText, Map<String, List<String>> headers, String body, Throwable throwable) {
		super(statusCode + ":" + statusText, null, throwable);

		this.statusCode = statusCode;
		this.statusText = statusText;
		this.entity = new Entity(headers, body);
	}

	@AllArgsConstructor
	public static class Entity {
		@Getter private Map<String, List<String>> headers;
		@Getter private String body;
	}
}
