package me.underly0.underlyapi.commons.files;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import me.underly0.underlyapi.api.file.FileObject;

import java.io.File;

@Getter
public abstract class FileBase {

    private final File file;
    private FileConfiguration config;
    private final FileObject type;
    private final Plugin plugin;

    public FileBase(FileObject type, Plugin plugin) {
        this.type = type;
        this.plugin = plugin;

        this.file = new File(plugin.getDataFolder(), type.getFileName());

        if (!file.exists()) {
            plugin.saveResource(type.getFileName(), false);
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
        config.load(file);
        load();
    }
}
