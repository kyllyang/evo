package com.github.framework.evo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2019-07-25 08:40
 * 响应码 Response Code
 */
public class SR {
	private static final Map<RC, String> MESSAGE_MAP = new HashMap<>();

	static {
		MESSAGE_MAP.put(RC.SUCC, "成功");
		MESSAGE_MAP.put(RC.FAIL_REQUEST_PARAM, "请求参数无效");
		MESSAGE_MAP.put(RC.FAIL_UNEXPECT, "不期望的内部服务异常");

		MESSAGE_MAP.put(RC.BASE_SPECIFICATION, "代码风格不符合框架规范");
		MESSAGE_MAP.put(RC.BASE_EXCEPTION, "未按要求转换业务异常");

		MESSAGE_MAP.put(RC.AUTH_USERNAME_NOT_FOUND, "用户名[%s]不存在");
		MESSAGE_MAP.put(RC.AUTH_PASSWORD_INVALID, "密码无效");
		MESSAGE_MAP.put(RC.AUTH_MOBILENUMBER_NOT_FOUND, "手机号[%s]不存在");
		MESSAGE_MAP.put(RC.AUTH_REFRESH_TOKEN_NOT_FOUND, "刷新令牌[%s]不存在");
		MESSAGE_MAP.put(RC.AUTH_REFRESH_TOKEN_VERIFY, "刷新令牌[%s]验证失败");
		MESSAGE_MAP.put(RC.AUTH_VERIFY_CODE_INVALID, "验证码[%s]无效");
		MESSAGE_MAP.put(RC.AUTH_VERIFY_CODE_SEND, "向[%s]发送验证码[%s]失败");
		MESSAGE_MAP.put(RC.AUTH_LOGOUT, "用户登出失败");
		MESSAGE_MAP.put(RC.AUTH_ACCESS_TOKEN_INVALID, "访问令牌无效");
	}

	public static String getMessage(RC rc, Object... args) {
		return String.format(MESSAGE_MAP.get(rc), args);
	}

	public static enum RC {
		SUCC("SUCC-0000"),
		FAIL_REQUEST_PARAM("FAIL-0001"),
		FAIL_UNEXPECT("FAIL-0001"),

		BASE_SPECIFICATION("BASE-0001"),
		BASE_EXCEPTION("BASE-0002"),

		AUTH_USERNAME_NOT_FOUND("AUTH-0001"),
		AUTH_PASSWORD_INVALID("AUTH-0002"),
		AUTH_MOBILENUMBER_NOT_FOUND("AUTH-0003"),
		AUTH_REFRESH_TOKEN_NOT_FOUND("AUTH-0004"),
		AUTH_REFRESH_TOKEN_VERIFY("AUTH-0005"),
		AUTH_VERIFY_CODE_INVALID("AUTH-0006"),
		AUTH_VERIFY_CODE_SEND("AUTH-0007"),
		AUTH_LOGOUT("AUTH-0008"),
		AUTH_ACCESS_TOKEN_INVALID("AUTH-0009");

		private String code;

		private RC(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}
}
