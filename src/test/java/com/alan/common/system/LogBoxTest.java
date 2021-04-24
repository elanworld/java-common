package com.alan.common.system;

import org.junit.Test;

public class LogBoxTest {
	@Test
	public void getLogger() {
		LogBox.getLogger().info("java util log test");
	}

	@Test
	public void getLog4j() {
		LogBox.getLog4j().info("log4j test");
	}
}
