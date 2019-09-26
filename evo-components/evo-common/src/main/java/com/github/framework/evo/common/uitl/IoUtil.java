package com.github.framework.evo.common.uitl;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: Kyll
 * Date: 2019-09-26 17:36
 */
@Slf4j
public class IoUtil {
	public static String toString(InputStream in) {
		StringBuilder sb = new StringBuilder();
		char[] buffer = new char[4096];
		int len;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			while ((len = br.read(buffer)) > 0) {
				sb.append(new String(buffer, 0, len));
			}
		} catch (IOException e) {
			log.error("读取InputStream失败", e);
		}

		return sb.toString();
	}
}
