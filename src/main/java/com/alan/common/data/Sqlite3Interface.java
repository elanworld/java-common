package com.alan.common.data;

import java.io.Closeable;

public interface Sqlite3Interface extends Closeable {
	void setDbFile(String dbFile);

	void runSql(String sqlCommand);
}
