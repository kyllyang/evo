package com.github.framework.evo.auth.exception;

import com.github.framework.evo.common.exception.BusinessException;

/**
 * User: Kyll
 * Date: 2019-01-04 13:57
 */
public class PasswordInvalidException extends BusinessException {
	public PasswordInvalidException(String message) {
		super(message);
	}
}
