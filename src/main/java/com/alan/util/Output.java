package com.alan.util;

import com.alan.system.ConsoleReader;
import com.alan.system.LogBox;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;


public class Output {
    private static StringBuilder line = new StringBuilder();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
    private static boolean filePrint = true;
    private static boolean consolePrint = true;
    private static boolean log = false;

    static {
        setFilePrint(filePrint);
    }

    public static <E> void print(E... objects) {
        Logger logger = LogBox.getLogger();

        parseDetail(objects);
        PrintStream filePrintStream = ConsoleReader.getFilePrintStream();
        filePrintStream.print(simpleDateFormat.format(new Date()));
        if (log) {
            logger.info(line.toString());
        }
        if (consolePrint) {
            System.out.print(line.toString());
        }
        line.delete(0, line.length());
    }

    public static void setFilePrint(boolean filePrint) {
        Output.filePrint = filePrint;
        if (filePrint) {
            ConsoleReader.start();
        } else {
            ConsoleReader.end();
        }
    }

    public static boolean isFilePrint() {
        return filePrint;
    }

    private static <E> void parseDetail(E... objects) {
        for (int i = 0; i < objects.length; i++) {
            try {
                line.append(String.valueOf(objects[i]));
                if (objects.length - 1 == i) {
                    line.append("\n");
                } else {
                    line.append(" ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void setConsolePrint(boolean consolePrint) {
        Output.consolePrint = consolePrint;
    }

    public static void setLog(boolean log) {
        Output.log = log;
    }

    public static boolean isConsolePrint() {
        return consolePrint;
    }

    public static boolean isLog() {
        return log;
    }
}
