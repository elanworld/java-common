package com.alan.system;

import com.alan.util.DateBox;
import com.alan.util.Output;

import java.io.File;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

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
            FileHandler fileHandler = new FileHandler(getLogPath(), true);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    long millis = record.getMillis();
                    Date date = DateBox.getDate(millis);
                    // String invokerClassName = Thread.currentThread().getStackTrace()[9].getClassName();
                    String time = DateBox.format(date, "yyyy-MM-dd HH:mm:ss");
                    String line = String.format("[%s] %s", time, record.getMessage());
                    return line;
                }
            });
            log.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLogPath() {
        if (logDirectory != null) {
            return logDirectory;
        }
        String logDirectory = System.getenv("MyResources");
        if (logDirectory != null) {
            return new File(logDirectory, "javaLog.log").toString();
        } else {
            return "javaLog.log";
        }
    }

    // log4j
    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LogBox.class.getName());

    public synchronized static org.apache.log4j.Logger getLog4j() {
        return logger;
    }

}
