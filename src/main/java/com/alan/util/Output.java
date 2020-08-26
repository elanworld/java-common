package com.alan.util;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.logging.*;


public class Output {

    private static boolean show = true;

    public static <E> void print(E... objects) {
        Logger log = LogBox.getInstance();
        String line = "";
        for (E object : objects) {
            try {
                String ob = String.valueOf(object);
                line += ob + " ";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        line += "\n";
        log.info(line);
        if (show) {
            System.out.print(line);
        }


    }

    public static void setShow(boolean show) {
        Output.show = show;
    }

    static class LogBox {
        private static Logger log;

        public static Logger getInstance() {
            if (log != null) {
                return log;
            } else {
                new LogBox();
                return log;
            }
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
            String logDictory = System.getenv("logDictory");
            if (logDictory != null) {
                return Path.of(logDictory, "javaLog.log").toString();
            } else {
                return "javaLog.log";
            }
        }

    }
}
