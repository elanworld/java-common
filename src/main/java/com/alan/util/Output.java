package com.alan.util;

import java.text.Format;
import java.util.Date;
import java.util.logging.*;


public class Output {
    private static Format format;
    private static boolean show = true;

    public static <E> void print(E... objects) {
        Logger log = new LogBox().getLog();
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
        // log.setLevel(Level.WARNING);
        log.info(line);
        if (show) {
            System.out.print(line);
        }

    }

    public static void setShow(boolean show) {
        Output.show = show;
    }

    static class LogBox {
        Logger log;

        public LogBox() {
            log = Logger.getLogger(Output.class.getName());
            try {
                FileHandler fileHandler = new FileHandler("log.log", true);
                fileHandler.setFormatter(new Formatter() {
                    @Override
                    public String format(LogRecord record) {
                        long millis = record.getMillis();
                        Date date = DateBox.getDate(millis);
                        String format = DateBox.format(date, "[yyyy-MM-dd HH:mm:ss] ");
                        String line = format + record.getMessage();
                        System.out.println(line);
                        return line;
                    }
                });
                log.addHandler(fileHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Logger getLog() {
            return log;
        }
    }
}
