/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.web.tans;

import org.junit.Test;

public class BaiduTranslatorTest {

	@Test
	public void translate() {
		for (int i = 0; i < 10; i++) {
			System.out.println(System.currentTimeMillis());
			BaiduTranslator.translate("nice to meet you", true);
		}
	}
}
