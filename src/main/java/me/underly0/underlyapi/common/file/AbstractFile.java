package me.underly0.underlyapi.common.file;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

@Getter
public abstract class AbstractFile {

    private final File file;
    private FileConfiguration config;
    private final String fileName;
    private final Plugin plugin;

    public AbstractFile(String fileName, Plugin plugin) {
        this.fileName = fileName;
        this.plugin = plugin;

        this.file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        load();
    }

    public abstract void postLoad();

    public void load() {
        this.config = YamlConfiguration.loadConfiguration(file);
        postLoad();
    }

    @SneakyThrows
    public void reload() {
        config.save(file);
        load();
    }
}
