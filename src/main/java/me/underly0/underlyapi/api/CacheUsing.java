package me.underly0.underlyapi.api;

import com.google.common.collect.Sets;
import me.underly0.underlyapi.api.database.Database;

import java.util.Set;

public class CacheUsing {
    public static final Set<Database> CASHED_CONNECTIONS = Sets.newConcurrentHashSet();
}
