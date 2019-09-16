package com.github.framework.evo.common.exception;

import com.github.framework.evo.common.SR;
import lombok.Getter;
import lombok.Setter;

/**
 * User: Kyll
 * Date: 2018-03-05 21:47
 *
 * 业务异常
 */
public class BusinessException extends BaseException {
	@Getter @Setter protected String code;
	@Getter @Setter protected SR.RC rc;

	public BusinessException(SR.RC rc, Object... args) {
		this(rc.getCode(), SR.getMessage(rc, args), null, null);
	}

	public BusinessException(SR.RC rc, Throwable throwable, Object... args) {
		this(rc.getCode(), SR.getMessage(rc, args), null, throwable);
	}

	public BusinessException(String message) {
		this(null, message, null, null);
	}

	public BusinessException(String message, Object data) {
		this(null, message, data, null);
	}

	public BusinessException(String message, Throwable throwable) {
		this(null, message, null, throwable);
	}

	public BusinessException(String code, String message) {
		this(code, message, null, null);
	}

	public BusinessException(String code, String message, Object data) {
		this(code, message, data, null);
	}

	public BusinessException(String code, String message, Throwable throwable) {
		this(code, message, null, throwable);
	}

	public BusinessException(String code, String message, Object data, Throwable throwable) {
		super(message, data, throwable);

		this.code = code;
	}
}
