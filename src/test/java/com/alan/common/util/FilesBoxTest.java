package com.alan.common.util;

import org.junit.Test;

public class FilesBoxTest {
	String file = "F:\\Alan\\Videos\\Mine\\published\\Douyin\\New_3.mp4";
	String dir = "F:\\Alan\\Videos\\Mine\\published\\Kauishou";

	@Test
	public void move() {
		FilesBox.move(file, dir);
	}

	@Test
	public void print() {
		Output.print("dd", "kk");
	}
}
