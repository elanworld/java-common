package com.alan.data;

import com.alan.system.PathsEnum;
import org.junit.Test;

public class SqliteBoxTest {
    @Test
    public void connect() {
        Sqlite3Box sqlite3Box = new Sqlite3Box(PathsEnum.TEST.getTypeName());
        sqlite3Box.runSql("create table if not exists test(name char(10));");
    }
}
