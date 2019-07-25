package com.github.framework.evo.common;

/**
 * User: Kyll
 * Date: 2018-02-28 14:33
 */
public class Const {
	// HTTP 状态码
	public static final int HTTP_STATUS_OK = 200;
	public static final int HTTP_STATUS_BAD_REQUEST = 400;
	public static final int HTTP_STATUS_BUSINESS_EXCEPTION = 499;
	public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
	// HTTP 头信息
	public static final String HTTP_HEADER_TOKEN = "Authorization";// 保存 access token，OAuth2框架规范
	public static final String HTTP_HEADER_USER_CONTEXT = "X-EVO-User-Context";// 保存 UserContext JSON，EVO框架自定义
	public static final String HTTP_HEADER_X_FORWARDED_FOR = "X-Forwarded-For";// 客户端真实IP，格式 X-Forwarded-For: client, proxy1, proxy2
	public static final String HTTP_HEADER_X_REAL_IP = "X-Real-IP";// Nginx设置的客户端真实IP
	public static final String HTTP_HEADER_USER_AGENT = "User-Agent";// 用户代理
	// URI Ant Path Pattern
	public static final String URI_ANT_ACTUATOR = "/actuator/**";
	// redis key 短信验证码前缀
	public static final String VERIFY_CODE_PREFIX = "VERIFY_CODE_";
	public static final String VERIFY_CODE_SIGN_IN = "SIGN_IN";
	public static final String VERIFY_CODE_SIGN_UP = "SIGN_UP";
	public static final String VERIFY_CODE_TYPE_RANDOM = "random";
	// redis key 业务标志位(bizz flag)
	public static final String BF_ONLINE = "ONLINE";
	public static final String BF_ACCESS_TOKEN = "ACCESS_TOKEN";
	public static final String BF_REFRESH_TOKEN = "REFRESH_TOKEN";
	public static final String BF_OLD_TOKEN = "OLD_TOKEN";
	// 专有名词，PN为proper noun的简写
	public static final String PN_UNKNOWN = "unknown";// 不确定
	// 加密解密
	public static final String CRYPTO_AES256_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	public static final String CRYPTO_AES256_IV_PARAMETER_SPEC = "1001100110011001";
	// JWT
	public static final String JWT_ALGORITHM = "HS512";// 加密方式
	public static final String JWT_TOKEN_PREFIX = "Bearer ";// 前缀
	public static final String JWT_CLAIMS_KEY = "content";// Claims 内容 key


	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

	public static final int BUFFER_SIZE_1024 = 1024;
	public static String[] NUMBER_CHARS = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static String[] WORD_CHARS = new String[] {
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "_"
	};
}
