package com.alan.common.data;

import org.junit.Test;

import com.alan.common.system.PathsEnum;

public class SqliteBoxTest {
	@Test
	public void connect() {
		Sqlite3Box sqlite3Box = new Sqlite3Box(PathsEnum.TEST.getPath());
		sqlite3Box.runSql("create table if not exists test(name char(10));");
	}
}
