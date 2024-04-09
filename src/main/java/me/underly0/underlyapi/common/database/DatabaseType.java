package me.underly0.underlyapi.common.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DatabaseType {
    SQLITE, MYSQL, ABSTRACT;
}
