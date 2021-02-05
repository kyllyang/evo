package com.github.framework.evo.common.uitl;

import com.github.framework.evo.common.exception.StringOperateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * User: Kyll
 * Date: 2018-02-09 14:59
 */
@Slf4j
public class StringUtil {
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	public static String toStringIfEmpty(Object object) {
		return object == null ? "" : object.toString();
	}

	public static String toStringIfNull(Object object) {
		return object == null ? null : object.toString();
	}

	public static String defaultIfBlank(String str, String defaultStr) {
		return StringUtils.defaultIfBlank(str, defaultStr);
	}

	/**
	 * 首字母大写，其余字符不变
	 * @param str 输入字符串
	 * @return 首字母大写的字符串
	 */
	public static String toCapture(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static String leftPad(String str, int size, char padChar) {
		return StringUtils.leftPad(str, size, padChar);
	}

	public static String leftPad(String str, int size, String padStr) {
		return StringUtils.leftPad(str, size, padStr);
	}

	/**
	 * 将驼峰字符串中大写字母、数字前插入下划线
	 * @param camel 驼峰形式字符串
	 * @return 带有下划线的字符串
	 */
	public static String camelStringInsertUnderline(String camel) {
		List<Integer> indexList = new ArrayList<>();
		for (int i = 0, length = camel.length(); i < length; i++) {
			char c = camel.charAt(i);

			int size = indexList.size();
			if (size == 0) {
				indexList.add(i);
			} else {
				char pc = camel.charAt(i - 1);
				if (Character.isDigit(pc) == Character.isDigit(c)) {
					if (Character.isLowerCase(pc) && Character.isUpperCase(c)) {
						indexList.add(i);
					}
				} else {
					indexList.add(i);
				}
			}
		}

		StringBuilder result = new StringBuilder();
		for (int i = 0, size = indexList.size() - 1; i < size; i++) {
			result.append(camel, indexList.get(i), indexList.get(i + 1)).append("_");
		}
		result.append(camel.substring(indexList.get(indexList.size() - 1)));

		return result.toString();
	}

	public static boolean antPathMatch(String pattern, String path) {
		return new AntPathMatcher().match(pattern, path);
	}

	public static String urlEncodeUTF8(String str) {
		try {
			return URLEncoder.encode(str, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new StringOperateException("字符串编码 UTF-8 失败", e);
		}
	}

	public static String urlDecodeUTF8(String str) {
		try {
			return URLDecoder.decode(str, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new StringOperateException("字符串解码 UTF-8 失败", e);
		}
	}

	public static byte[] toUTF8Byte(String str) {
		try {
			return str.getBytes(StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new StringOperateException("字符编码转换 UTF-8 异常", e);
		}
	}

	public static String toUnicode(String str) {
		StringBuilder unicode = new StringBuilder();
		for (char c : str.toCharArray()) {
			if (c <= 127) {// 标准ASCII范围内的字符，直接输出
				unicode.append(c);
				continue;
			}

			unicode.append("\\u");

			String hexString = Integer.toHexString(c);
			if (hexString.length() < 4) {// 不够四位进行补0操作
				unicode.append("0000", hexString.length(), 4);
			}
			unicode.append(hexString);
		}
		return unicode.toString();
	}

	public static String[] split(String str, String... delimiteds) {
		if (isBlank(str)) {
			return null;
		}
		if (delimiteds == null || delimiteds.length == 0) {
			return new String[]{str};
		}

		char c = delimiteds[0].charAt(0);

		int pos = str.indexOf(c);
		if (pos < 0) {
			return new String[]{str};
		}
		List<String> strList = new ArrayList<>();
		if (pos == 0) {
			strList.add("");
		} else {
			strList.add(str.substring(0, pos));
		}
		while (pos >= 0) {
			int end = str.indexOf(c, pos + 1);
			if (end < 0) {
				end = str.length();
			}
			if (end - pos == 1) {
				strList.add("");
			} else {
				strList.add(str.substring(pos + 1, end));
			}
			pos = str.indexOf(c, pos + 1);
		}
		return strList.toArray(new String[0]);
	}

	public static byte[] ungzip(byte[] bytes) {
		byte[] result = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPInputStream gzip = null;
		try {
			gzip = new GZIPInputStream(new ByteArrayInputStream(bytes));

			byte[] buffer = new byte[1024];
			int n;
			while ((n = gzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}

			result = out.toByteArray();
		} catch (IOException e) {
			log.error("解压gzip错误", e);
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					log.error("关闭gzip流错误", e);
				}
			}

			try {
				out.close();
			} catch (IOException e) {
				log.error("关闭out流错误", e);
			}
		}

		return result;
	}
}
