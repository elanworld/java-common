package com.alan.common.web;

import java.util.Map;

public interface ApacheHttp {
	String get(String url);

	/**
	 * post method
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	String post(String url, Map<String, String> param);
}
