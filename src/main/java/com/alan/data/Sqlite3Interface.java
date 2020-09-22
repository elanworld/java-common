package com.alan.data;

public interface Sqlite3Interface {
    void setDbFile(String dbFile);
    void runSql(String sqlCommand);
    void close();
}
