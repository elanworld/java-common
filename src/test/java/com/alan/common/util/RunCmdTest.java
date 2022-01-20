/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.util;

import org.junit.Test;

public class RunCmdTest {

	@Test
	public void run() {
		new RunCmd("curl baidu.com");
	}
}
