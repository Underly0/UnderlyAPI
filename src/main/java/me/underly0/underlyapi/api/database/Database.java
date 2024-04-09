package me.underly0.underlyapi.api.database;

import me.underly0.underlyapi.common.database.DatabaseType;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface Database {
    DatabaseType getDatabaseType();
    void connect();
    void close();
    Connection getConnection();
    Statement getStatement();
    boolean hasConnection();
    boolean hasStatement();
    List<Map<String, Object>> executeQuery(String sql, Object... objects);
    void executeUpdates(List<String> sqls);
    void executeUpdate(String sql, Object... objects);

}
