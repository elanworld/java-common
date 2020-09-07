package com.alan.util;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.logging.*;


public class Output {

    private static String line = "";
    private static boolean show = true;
    private static boolean log = true;

    public static <E> void print(E... objects) {
        Logger logger = LogBox.getInstance();
        getStr(objects);
        if (log) {
            logger.info(line);
        }
        if (show) {
            System.out.print(line);
        }
        line = "";
    }

    private static <E> void getStr(E... objects) {
        for (Object object : objects) {
            try {
                line += String.valueOf(object) + " ";
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        line += "\n";
    }

    public static void setShow(boolean show) {
        Output.show = show;
    }

    public static void setLog(boolean log) {
        Output.log = log;
    }

    static class LogBox {
        private static volatile Logger log;

        public static synchronized Logger getInstance() {
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
                        String format = DateBox.format(date, "[yyyy-MM-dd HH:mm:ss] ");
                        String line = format + record.getMessage();
                        return line;
                    }
                });
                log.addHandler(fileHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String getLogPath() {
            String logDictory = System.getenv("LogDictory");
            if (logDictory != null) {
                return new File(logDictory, "javaLog.log").toString();
            } else {
                return "javaLog.log";
            }
        }

    }
}
