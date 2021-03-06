/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.web.tans;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.alan.common.util.Output;
import com.alan.common.util.StringBox;
import com.alan.common.web.ApacheHttpBox;

public class YoudaoTranslator {
	private static final String API_URL = "https://openapi.youdao.com/api";
	private static final String APP_KEY = "6c842a4c5eb6d2fd";
	private static final String APP_SECRET = "ZwB1YbaIaqBchv8FdR7VU8rGBnkYQrXU";

	ApacheHttpBox apacheHttpBox = new ApacheHttpBox();

	public String tans(String word) {
		Map<String, String> params = new HashMap<>();
		params.put("q", word);
		params.put("from", "auto");
		params.put("to", "auto");
		params.put("signType", "v3");
		String curtime = String.valueOf(System.currentTimeMillis() / 1000);
		params.put("curtime", curtime);
		params.put("appKey", APP_KEY);
		String salt = String.valueOf(System.currentTimeMillis());
		String signStr = APP_KEY + word + salt + curtime + APP_SECRET;
		String sign = getDigest(signStr);
		params.put("salt", salt);
		params.put("sign", sign);
		String content = apacheHttpBox.post(this.API_URL, params);
		String translation = getTranslation(content);
		Output.print("translation:", word, translation);
		return translation;
	}

	/**
	 * 生成加密字段
	 */
	private static String getDigest(String string) {
		if (string == null) {
			return null;
		}
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
		try {
			MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 过滤 关键字
	 *
	 * @param content
	 * @return
	 */
	private String getTranslation(String content) {
		List<String> line = StringBox.findGroup(new ArrayList<>(Collections.singletonList(content)),
				".*\"translation\":.\"(.*?)\".,.*");
		if (line.isEmpty()) {
			return "";
		}
		return line.get(0);
	}
}
