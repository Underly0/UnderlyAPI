package me.underly0.underlyapi.impl.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.SneakyThrows;
import me.underly0.underlyapi.commons.database.DatabaseConfig;
import org.bukkit.plugin.Plugin;
import me.underly0.underlyapi.commons.CacheUsing;
import me.underly0.underlyapi.api.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseImpl implements Database {

    @Getter
    private final Plugin plugin;
    private DatabaseConfig dbConfig;
    private HikariDataSource source;
    private Connection connection;
    private Statement statement;
    private HikariConfig config;

    public DatabaseImpl(Plugin plugin, DatabaseConfig dbConfig) {
        this.plugin = plugin;
        this.dbConfig = dbConfig;
        connect();
        CacheUsing.CASHED_CONNECTIONS.add(this);
    }

    public DatabaseImpl(Plugin plugin) {
        this.plugin = plugin;
        connect();
        CacheUsing.CASHED_CONNECTIONS.add(this);
    }

    @Override
    @SneakyThrows
    public void executeUpdate(String sql, Object... objects) {
        getStatement().executeUpdate(replaceSql(sql, objects));
    }

    @Override
    @SneakyThrows
    public void executeUpdates(List<String> sql) {
        for (String s : sql)
            getStatement().executeUpdate(s);
    }


    @Override
    public List<Map<String, Object>> executeQuery(String sql, Object... objects) {
        List<Map<String, Object>> result = new ArrayList<>();

        try {
            ResultSet resultSet = getStatement().executeQuery(replaceSql(sql, objects));

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                int columns = resultSet.getMetaData().getColumnCount();

                for (int i = 1; i <= columns; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnName, value);
                }

                result.add(row);
            }
        } catch (Exception e) {}

        return result;
    }

    private void connect() {
        if (config == null)
            config = dbConfig == null
                    ? getHikariConfigSQLite()
                    : getHikariConfigMysql();

        source = new HikariDataSource(config);
    }

    private HikariConfig getHikariConfigSQLite() {
        HikariConfig config = new HikariConfig();

        String url = String.format("jdbc:sqlite:/%s/database.db", plugin.getDataFolder().getAbsolutePath());

        config.setPoolName(String.format("SQLite | UnderlyAPI (%s)", plugin.getName()));
        config.setJdbcUrl(url);
        config.setConnectionTimeout(1000);
        return config;
    }

    private HikariConfig getHikariConfigMysql() {
        HikariConfig config = new HikariConfig();

        String url = String.format("jdbc:mysql://%s:3306/%s" +
                        "?useSSL=false" +
                        "&autoReconnect=true" +
                        "&verifyServerCertificate=false" +
                        "&characterEncoding=utf8" +
                        "&useUnicode=true" +
                        "&autoReconnect=true",
                dbConfig.getHost(), dbConfig.getBase());

        config.setPoolName(String.format("MySQL | UnderlyAPI (%s)", plugin.getName()));
        config.setJdbcUrl(url);
        config.setUsername(dbConfig.getUser());
        config.setPassword(dbConfig.getPass());

        config.setConnectionTimeout(1000);
        return config;
    }

    @Override
    public boolean hasSourceNull() {
        return source == null || source.isClosed();
    }

    @Override
    @SneakyThrows
    public boolean hasConnectionNull() {
        return connection == null || connection.isClosed();
    }

    @Override
    @SneakyThrows
    public boolean hasStatementNull() {
        return statement == null || statement.isClosed();
    }

    @Override
    public HikariDataSource getSource() {
        if (hasSourceNull())
            connect();

        return source;
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        if (hasConnectionNull())
            connection = getSource().getConnection();

        return connection;
    }

    @SneakyThrows
    @Override
    public Statement getStatement() {
        if (hasStatementNull())
            statement = getConnection().prepareStatement("");

        return statement;
    }

    public String replaceSql(String sql, Object[] objects) {
        return objects.length != 0 ? String.format(sql.replace("?", "%s"), objects) : sql;
    }

    @Override
    @SneakyThrows
    public void reConnect() {
        if (!source.isClosed())
            source.close();

        connect();
    }

    @Override
    public void closeSource() {
        if (!(source == null || source.isClosed()))
            source.close();
    }

    @Override
    public void close() {
        closeSource();
        CacheUsing.CASHED_CONNECTIONS.remove(this);
    }
}
