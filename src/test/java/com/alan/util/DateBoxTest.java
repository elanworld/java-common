/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.util;

import java.util.Date;

import junit.framework.TestCase;

public class DateBoxTest extends TestCase {

	public void testTestFormat() {
		String format = DateBox.format(new Date(1614358220 * 1000L), "YYYY-MM-dd HH:mm:ss");
		assertEquals(format, "2021-02-27 00:50:20");
	}
}
