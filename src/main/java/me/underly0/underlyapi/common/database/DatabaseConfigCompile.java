package me.underly0.underlyapi.common.database;

import me.underly0.underlyapi.api.database.Database;
import me.underly0.underlyapi.common.database.impl.MySQLDatabase;
import me.underly0.underlyapi.common.database.impl.SQLiteDatabase;
import me.underly0.underlyapi.common.object.DatabaseSection;
import org.bukkit.plugin.Plugin;

public class DatabaseConfigCompile {

    private DatabaseConfigCompile() {
    }

    public static Database create(Plugin plugin, DatabaseSection databaseSection) {
        DatabaseType type = DatabaseType.valueOf(databaseSection.getType());

        switch (type) {
            case MYSQL: {
                DatabaseSection.MySQLSection mysqlSection = databaseSection.getMysqlSection();
                MySQLDatabase.DatabaseInfo databaseInfo = new MySQLDatabase.DatabaseInfo(
                        mysqlSection.getHost(), mysqlSection.getUsername(),
                        mysqlSection.getPassword(), mysqlSection.getDatabase()
                );

                return MySQLDatabase.create(databaseInfo, mysqlSection.getParams());
            }
            case SQLITE: {
                DatabaseSection.SQLiteSection sqliteSection = databaseSection.getSqliteSection();

                return SQLiteDatabase.create(plugin, sqliteSection.getFileName(), sqliteSection.getParams());
            }
            default:
            case ABSTRACT: {
                throw new RuntimeException("Данный тип не поддерживает быстрое создание");
            }
        }
    }
}
