package com.alan.system;

public enum PathsEnum {
    MY_DB("G:\\Alan\\Documents\\OneDrive\\Documents\\program\\resources\\mydata.db"),
    TEST("G:\\Alan\\Documents\\OneDrive\\Documents\\program\\resources\\test.db");

    private String typeName;

    PathsEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
