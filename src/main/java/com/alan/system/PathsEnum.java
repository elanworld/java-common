package com.alan.system;

import java.io.File;

public enum PathsEnum {
    MY_DB("G:\\Alan\\Documents\\OneDrive\\Documents\\program\\resources\\data_box.db"),
    TEST("G:\\Alan\\Documents\\OneDrive\\Documents\\program\\resources\\test.db"),
    LOG_PATH(getLogPath());

    private static String logDirectory;

    private String typeName;

    PathsEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getPath() {
        return typeName;
    }

    private static String getLogPath() {
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
}
