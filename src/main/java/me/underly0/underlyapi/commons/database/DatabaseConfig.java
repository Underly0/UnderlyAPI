package me.underly0.underlyapi.commons.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DatabaseConfig {
    private final String host;
    private final String user;
    private final String pass;
    private final String base;
}
