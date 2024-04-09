package me.underly0.underlyapi;

import lombok.Getter;
import me.underly0.underlyapi.service.MenuService;
import org.bukkit.plugin.java.JavaPlugin;

public final class APILoader extends JavaPlugin {

    @Getter
    private static APILoader instance;

    @Override
    public void onEnable() {
        instance = this;

        initServices();
    }
    public void initServices() {
        //new UpdaterService(this, "Underly0", "UnderlyAPI");
        new MenuService(this);
    }

    @Override
    public void onDisable() {
    }

}
