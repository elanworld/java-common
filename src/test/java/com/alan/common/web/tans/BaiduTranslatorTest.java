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
		new BaiduTranslator().translate("nice to meet you", true);
	}
}
