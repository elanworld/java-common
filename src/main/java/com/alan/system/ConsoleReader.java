package com.alan.system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleReader {
	final private static PrintStream out = System.out;
	final private static PrintStream err = System.err;
	private static volatile PrintStream filePrintStream;

	public static void start() {
		start(PathsEnum.LOG_PATH.getPath());
	}

	public static void start(String logFile) {
		try {
			filePrintStream = new PrintStream(new FileOutputStream(logFile, true));
			filePrintStream.println();
			filePrintStream.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) {
				out.write(b);
				filePrintStream.write(b);
			}
		}));
		System.setErr(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) {
				err.write(b);
				filePrintStream.write(b);

			}
		}));
	}

	public static void end() {
		System.setOut(out);
		System.setErr(err);
	}

	public static PrintStream getFilePrintStream() {
		if (filePrintStream == null) {
			start();
		}
		return filePrintStream;
	}
}
