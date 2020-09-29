package com.alan.data;

import com.alan.data.Sqlite3Box;
import com.alan.system.PathsBox;
import org.junit.Test;

public class SqliteBoxTest {
    @Test
    public void connect() {
        Sqlite3Box sqlite3Box = new Sqlite3Box(PathsBox.TEST.getTypeName());
        sqlite3Box.runSql("create table if not exists test(name char(10));");
    }
}
