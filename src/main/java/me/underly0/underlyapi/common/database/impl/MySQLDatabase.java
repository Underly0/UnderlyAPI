package me.underly0.underlyapi.common.database.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.underly0.underlyapi.common.database.AbstractDatabase;
import me.underly0.underlyapi.api.database.Database;
import me.underly0.underlyapi.common.database.DatabaseType;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLDatabase extends AbstractDatabase {

    private final DatabaseInfo databaseInfo;
    private final List<String> params;
    private String url;
    private String param;

    private MySQLDatabase(DatabaseInfo databaseInfo, List<String> params) {
        super(DatabaseType.MYSQL);
        this.databaseInfo = databaseInfo;
        this.params = params;
    }

    public static Database create(DatabaseInfo databaseInfo, List<String> params) {
        MySQLDatabase database = new MySQLDatabase(databaseInfo, params);

        database.build();
        database.connect();
        return database;
    }

    private void build() {
        String host = databaseInfo.getHost();
        String base = databaseInfo.getDatabase();

        this.url = String.format("jdbc:mysql://%s:3306/%s", host, base);
        this.param = super.paramsBuilder(params);
    }

    @Override
    public void connect() {
        String username = databaseInfo.getUsername();
        String password = databaseInfo.getPassword();

        try {
            super.connection = DriverManager.getConnection(url + param, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных: "+e);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DatabaseInfo {
        private String host;
        private String username;
        private String password;
        private String database;
    }
}
