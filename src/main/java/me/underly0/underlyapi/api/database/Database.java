package me.underly0.underlyapi.api.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface Database {
    HikariDataSource getSource();
    Connection getConnection();
    boolean hasSourceNull();
    boolean hasConnectionNull();
    boolean hasStatementNull();
    Statement getStatement();
    void executeUpdate(String sql, Object... replace);
    void executeUpdates(List<String> sql);
    List<Map<String, Object>> executeQuery(String sql, Object... replace);
    void reConnect();

    void closeSource();

    void close();
}
