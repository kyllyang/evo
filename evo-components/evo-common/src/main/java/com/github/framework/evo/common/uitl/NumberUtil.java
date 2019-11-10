package com.github.framework.evo.common.uitl;

/**
 * User: Kyll
 * Date: 2019-11-10 11:46
 */
public class NumberUtil {
	public static Integer toInteger(String str) {
		if (StringUtil.isBlank(str)) {
			return null;
		}

		return Integer.parseInt(str.replaceAll("^(0+)", ""));
	}
}
