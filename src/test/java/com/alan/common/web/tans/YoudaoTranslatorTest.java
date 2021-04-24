/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.web.tans;

import org.junit.Assert;
import org.junit.Test;

public class YoudaoTranslatorTest {

	@Test
	public void tans() {
		String good = new YoudaoTranslator().tans("good");
		Assert.assertNotNull(good);
	}
}
