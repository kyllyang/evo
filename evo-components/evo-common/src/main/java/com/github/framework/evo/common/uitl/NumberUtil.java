package com.github.framework.evo.common.uitl;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

	public static double round(double value, int scale) {
		return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}
}
