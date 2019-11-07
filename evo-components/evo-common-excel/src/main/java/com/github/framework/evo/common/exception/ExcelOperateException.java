package com.github.framework.evo.common.exception;

/**
 * User: Kyll
 * Date: 2019-11-07 13:57
 */
public class ExcelOperateException extends BaseException {
	public ExcelOperateException(String message) {
		super(message, null, null);
	}

	public ExcelOperateException(String message, Object data) {
		super(message, data, null);
	}

	public ExcelOperateException(String message, Throwable throwable) {
		super(message, null, throwable);
	}

	public ExcelOperateException(String message, Object data, Throwable throwable) {
		super(message, data, throwable);
	}
}
