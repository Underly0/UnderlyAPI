package me.underly0.underlyapi.api.command;

import org.bukkit.plugin.Plugin;

import java.util.List;

public interface Command {
    String getName();
    List<String> getAliases();
    String getLabel();
    Plugin getPlugin();
    void register();
    void unregister();
}
