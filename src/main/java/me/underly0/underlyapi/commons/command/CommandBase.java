package me.underly0.underlyapi.commons.command;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public abstract class CommandBase extends Command {
    private final Plugin plugin;
    public CommandBase(Plugin plugin, String command, String... aliases) {
        this(false, plugin, command, aliases);
    }

    public CommandBase(boolean async, Plugin plugin, String command, String... aliases) {
        super(command);
        this.plugin = plugin;

        if (aliases.length != 0)
            super.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String lbl, String[] args) {
        command(sender, lbl, args);
        return true;
    }

    public abstract void command(CommandSender sender, String lbl, String[] args);

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args)
            throws IllegalArgumentException {
        List<String> complete = tabComplete(sender, args);

        if (complete != null)
            return complete;

        return super.tabComplete(sender, alias, args);
    }

    public abstract List<String> tabComplete(CommandSender sender, String[] args);
    public void register() {
        Bukkit.getCommandMap().register(super.getName(), this);
    }
    public void unregister() {
        Map<String, Command> knownCommands = Bukkit.getCommandMap().getKnownCommands();
        knownCommands.values().removeIf(cmd -> cmd.getName().equalsIgnoreCase(super.getName()));
    }

}
