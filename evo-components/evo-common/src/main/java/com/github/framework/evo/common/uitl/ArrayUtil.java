package com.github.framework.evo.common.uitl;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * User: Kyll
 * Date: 2018-05-11 09:23
 */
public class ArrayUtil {
	public static boolean isNull(Object[] values) {
		return values == null;
	}

	public static boolean isNotNull(Object[] values) {
		return !isNull(values);
	}

	public static boolean isEmpty(Object[] values) {
		return isNull(values) || values.length == 0;
	}

	public static boolean isNotEmpty(Object[] values) {
		return !isEmpty(values);
	}

	/**
	 * 检查数组元素有效性，其中任意元素为null，即无效
	 * 此方法并不会对数组本身进行检验
	 * @param values 待检验数组
	 * @return 如果待检验数组为null，或者检验通过为true， 否则为false
	 */
	public static boolean isValid(Long[] values) {
		boolean valid = true;
		if (values != null) {
			for (Long value : values) {
				if (value == null) {
					valid = false;
					break;
				}
			}
		}
		return valid;
	}

	public static boolean isIn(String value, String[] strs) {
		boolean result = false;

		if (strs != null) {
			for (String str : strs) {
				if (str == null) {
					if (value == null) {
						result = true;
						break;
					}
				} else {
					if (str.equals(value)) {
						result = true;
						break;
					}
				}
			}
		}

		return result;
	}

	/**
	 * 数组values中的元素全部存在于数组samples，则返回true
	 * @param values 待比较数组
	 * @param samples 样本数组
	 * @return 全部存在于数组samples，则返回true
	 */
	public static boolean isIn(String[] values, String[] samples) {
		return Arrays.stream(values).allMatch(value -> Arrays.asList(samples).contains(value));
	}

	/**
	 * 数组values中的元素任何一个不在数组samples中，则返回true
	 * @param values 待比较数组
	 * @param samples 样本数组
	 * @return 任何一个不在数组samples中，则返回true
	 */
	public static boolean isNotIn(String[] values, String[] samples) {
		return Arrays.stream(values).anyMatch(value -> !Arrays.asList(samples).contains(value));
	}

	public static String[] concat(String[]... as) {
		List<String> list = new ArrayList<>();
		Arrays.stream(as).filter(ArrayUtil::isNotEmpty).forEach(array -> list.addAll(Arrays.asList(array)));
		return list.toArray(new String[0]);
	}

	public static String join(String[] values, String delimited) {
		return StringUtils.join(values, delimited);
	}

	public static String join(Object[] values, String delimited) {
		return StringUtils.join(values, delimited);
	}

	public static String join(Iterator<?> iterator, String delimited) {
		return StringUtils.join(iterator, delimited);
	}

	/**
	 * 获取数组长度，当数组为null时，返回 0
	 * @param objects 数组
	 * @return 数组长度
	 */
	public static int length(Object[] objects) {
		return objects == null ? 0 : objects.length;
	}

	public static Integer[] toIntegerArray(String... strs) {
		Integer[] result = new Integer[strs.length];
		for (int i = 0, length = strs.length; i < length; i++) {
			result[i] = StringUtil.isBlank(strs[i]) ? null : NumberUtil.toInteger(strs[i]);
		}
		return result;
	}
}
