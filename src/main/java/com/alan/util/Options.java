package com.alan.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Options {
	public static void sleep(int second) {
		try {
			Output.print("wait for(seconds): " + second);
			TimeUnit.SECONDS.sleep(second);
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
			Output.print(String.format("you input tes:[%s]", line));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}
