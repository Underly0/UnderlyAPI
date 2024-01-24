package me.underly0.underlyapi;

import me.underly0.underlyapi.api.database.Database;
import me.underly0.underlyapi.commons.CacheUsing;
import me.underly0.underlyapi.service.menu.MenuService;
import me.underly0.underlyapi.service.updater.UpdaterService;
import me.underly0.underlyapi.test.command.TestCommand;
import me.underly0.underlyapi.test.event.TestEvent;
import org.bukkit.Bukkit;
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
        new UpdaterService(this, "Underly0", "UnderlyAPI");
        new MenuService(this);
    }
    public void initTests() {
        new TestCommand(this, "test").register();
        Bukkit.getPluginManager().registerEvents(new TestEvent(), this);
    }

    @Override
    public void onDisable() {
        CacheUsing.CASHED_CONNECTIONS.forEach(Database::close);
    }
}
