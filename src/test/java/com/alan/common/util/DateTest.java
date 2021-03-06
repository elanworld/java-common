package com.alan.common.util;

import java.io.PrintStream;
import java.util.Date;

import org.junit.Test;

public class DateTest {
	@Test
	public void parse() {
		String yyyy = DateBox.format(new Date(), "yyyy");
		Output.print(yyyy);
	}

	@Test
	public void get() {
		long time = new Date().getTime();
		time += 600000;
		Date date = DateBox.getDate(time);
		Output.print(date, time);
	}

	@Test
	public void cmd() {
		PrintStream orgStdout = null;
		PrintStream fileStdout = null;

		orgStdout = System.out;
		orgStdout.write(5);
	}
}
