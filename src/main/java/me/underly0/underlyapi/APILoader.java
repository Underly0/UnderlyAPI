package me.underly0.underlyapi;

import me.underly0.underlyapi.api.CacheUsing;
import me.underly0.underlyapi.api.database.Database;
import me.underly0.underlyapi.service.menu.MenuService;
import org.bukkit.plugin.java.JavaPlugin;

public final class APILoader extends JavaPlugin {
    private static APILoader inst;

    public static APILoader getInstance() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;

        initServices();
        //initTests();
    }
    public void initServices() {
        new MenuService(this);
    }
    public void initTests() {
    }

    @Override
    public void onDisable() {
        CacheUsing.CASHED_CONNECTIONS.forEach(Database::close);
    }
}
