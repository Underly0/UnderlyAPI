package me.underly0.underlyapi;

import lombok.Getter;
import me.underly0.underlyapi.api.UnderlyAPI;
import me.underly0.underlyapi.common.modules.PluginModules;
import me.underly0.underlyapi.service.MenuService;
import org.bukkit.plugin.java.JavaPlugin;

public final class APILoader extends JavaPlugin {

    @Getter
    private static APILoader instance;

    private PluginModules pluginModules;

    @Override
    public void onEnable() {
        instance = this;

        initModules();
        initServices();
    }
    public void initModules() {
        this.pluginModules = new PluginModules();
    }
    public void initServices() {
        //new UpdaterService(this, "Underly0", "UnderlyAPI");
        new MenuService();
    }

    @Override
    public void onDisable() {
        pluginModules.unloadModules();
    }

}
