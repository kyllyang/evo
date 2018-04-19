package com.ritoinfo.framework.evo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2018-02-28 14:33
 */
public class Const {
	public static final int HTTP_STATUS_OK = 200;
	public static final int HTTP_STATUS_BAD_REQUEST = 400;
	public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;

	public static final String RC_SUCC = "SUCC-0000";
	public static final String RC_FAIL_REQUEST_PARAM = "FAIL-0001";
	public static final String RC_FAIL_UNEXPECT = "FAIL-0002";

	public static final String RC_BASE_SPECIFICATION = "BASE-0001";
	public static final String RC_BASE_EXCEPTION = "BASE-0002";

	public static final String RC_AUTH_LOGIN = "AUTH-0001";
	public static final String RC_AUTH_LOGOUT = "AUTH-0002";
	public static final String RC_AUTH_REFRESH = "AUTH-0003";
	public static final String RC_AUTH_TRY_REFRESH = "AUTH-0004";
	public static final String RC_AUTH_VERIFY = "AUTH-0005";

	public static final String JWT_TOKEN_HEADER = "X-Authorization";

	private static final Map<String, String> RCM_MAP = new HashMap<>();

	static {
		RCM_MAP.put(RC_SUCC, "成功");
		RCM_MAP.put(RC_FAIL_REQUEST_PARAM, "请求参数无效");
		RCM_MAP.put(RC_FAIL_UNEXPECT, "不期望的内部服务异常");

		RCM_MAP.put(RC_BASE_SPECIFICATION, "代码风格不符合框架规范");
		RCM_MAP.put(RC_BASE_EXCEPTION, "未按要求转换业务异常");

		RCM_MAP.put(RC_AUTH_LOGIN, "用户名或密码无效");
		RCM_MAP.put(RC_AUTH_LOGOUT, "用户注销失败");
		RCM_MAP.put(RC_AUTH_REFRESH, "刷新令牌失败");
		RCM_MAP.put(RC_AUTH_TRY_REFRESH, "尝试刷新令牌失败");
		RCM_MAP.put(RC_AUTH_VERIFY, "校验令牌失败");
	}

	public static String getRcm(String rc) {
		return RCM_MAP.get(rc);
	}
}
