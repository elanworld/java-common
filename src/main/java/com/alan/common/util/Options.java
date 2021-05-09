package com.alan.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Options {
	public static void sleep(double second) {
		try {
			Output.print(String.format("wait for %s seconds", second));
			Thread.sleep((long) (second * 1000L));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String waitForInput() {
		String line = "";
		try {
			Output.print("please input:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			line = br.readLine();
			Output.print(String.format("you input is:[%s]", line));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}
