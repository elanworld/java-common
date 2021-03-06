/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.web.tans;

import com.alan.common.util.Output;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BaiduTranslator {

	private static final String APP_ID = "20210307000718292";
	private static final String SECURITY_KEY = "1N3ocBDQzGCBsZr6iMBi";
	private static long lastRunTime = 0;
	private static final int total = 10;

	public static String translate(String query, boolean toZh) {
		runWait();
		TransApi api = new TransApi(APP_ID, SECURITY_KEY);
		String to = "en";
		if (toZh) {
			to = "zh";
		}
		String res = api.getTransResult(query, "auto", to);
		JSONObject jsonObject = JSONObject.parseObject(res);
		String result = "";
		JSONArray trans_result = null;
		try {
			trans_result = jsonObject.getJSONArray("trans_result");
			result = trans_result.getJSONObject(0).get("dst").toString();
		} catch (Exception ignored) {
			Output.print(trans_result);
		}
		Output.print("baidu translator:", query, "-->", result);
		return result;
	}

	/**
	 * 认证用户api 间隔10ms
	 */
	private static void runWait() {
		long timeMillis = System.currentTimeMillis();
		long duration = timeMillis - lastRunTime;
		if (duration < total) {
			try {
				Thread.sleep(total - duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lastRunTime = System.currentTimeMillis();
	}

}
