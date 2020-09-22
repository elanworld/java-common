package com.alan.data;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Sqlite3Box implements Sqlite3Interface{
    String dbFile;
    Connection connection;
    Statement statement;

    public Sqlite3Box(String dbFile) {
        setDbFile(dbFile);
        connect();
    }

    @Override
    public void setDbFile(String dbFile) {
        this.dbFile = dbFile;
    }

    @Override
    public void runSql(String sqlCommand) {
        runSql(sqlCommand,false);
    }

    @Override
    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        if (dbFile == null) {
            throw new NullPointerException("set db file first");
        }
        try {
            new JDBC();
             connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
             statement = connection.createStatement();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public ResultSet runSql(String sqlCommand, boolean returnResult) {
        try {
            if (returnResult) {
                ResultSet resultSet = statement.executeQuery(sqlCommand);
                return resultSet;
            } else {
                statement.execute(sqlCommand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
