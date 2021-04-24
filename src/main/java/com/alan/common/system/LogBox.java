package com.alan.common.system;

import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.alan.common.util.DateBox;

public class LogBox {

	private static volatile Logger log;
	private static String logDirectory;

	public static synchronized Logger getLogger(String logPath) {
		logDirectory = logPath;
		if (log == null) {
			new LogBox();
		}
		return log;
	}

	public static synchronized Logger getLogger() {
		if (log == null) {
			new LogBox();
		}
		return log;
	}

	private LogBox() {
		log = Logger.getGlobal();
		log.setUseParentHandlers(false);
		try {
			FileHandler fileHandler = new FileHandler(PathsEnum.LOG_PATH.getPath(), true);
			fileHandler.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord record) {
					long millis = record.getMillis();
					Date date = DateBox.getDate(millis);
					StackTraceElement invoker = Thread.currentThread().getStackTrace()[8];
					String time = DateBox.format(date, "yyyy-MM-dd HH:mm:ss");
					return String.format("[%s] %s.%s:%s %s", time, invoker.getClassName(), invoker.getMethodName(),
							invoker.getLineNumber(), record.getMessage());
				}
			});
			log.addHandler(fileHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static org.apache.log4j.Logger logger;

	/**
	 * log4j
	 *
	 * @return log4j
	 */
	public synchronized static org.apache.log4j.Logger getLog4j() {
		if (logger == null) {
			StackTraceElement invoker = Thread.currentThread().getStackTrace()[2];
			logger = org.apache.log4j.Logger.getLogger(invoker.getClassName());
		}
		return logger;
	}
}
