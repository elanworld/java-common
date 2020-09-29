package com.alan.util;

import com.alan.system.LogBox;

import java.util.logging.*;


public class Output {

    private static String line = "";
    private static boolean show = true;
    private static boolean log = true;

    public static <E> void print(E... objects) {
        Logger logger = LogBox.getLogger();
        parseDetail(objects);
        if (log) {
            logger.info(line);
        }
        if (show) {
            System.out.print(line);
        }
        line = "";
    }

    private static <E> void parseDetail(E... objects) {
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

    public static boolean isShow() {
        return show;
    }

    public static boolean isLog() {
        return log;
    }
}
