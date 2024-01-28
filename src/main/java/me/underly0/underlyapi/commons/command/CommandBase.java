package me.underly0.underlyapi.commons.command;

import lombok.Getter;
import lombok.SneakyThrows;
import me.underly0.underlyapi.commons.command.event.list.PlayerPreCommandEvent;
import me.underly0.underlyapi.commons.command.event.list.PlayerUseCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@Getter
public abstract class CommandBase extends Command implements me.underly0.underlyapi.api.command.Command {
    private final Plugin plugin;
    private final boolean async;

    public CommandBase(Plugin plugin, String command, String... aliases) {
        this(false, plugin, command, aliases);
    }

    public CommandBase(boolean async, Plugin plugin, String command, String... aliases) {
        super(command);
        this.plugin = plugin;
        this.async = async;

        if (aliases.length != 0)
            super.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String lbl, String[] args) {
        if (async)
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
                    executeCommand(sender, lbl, args));
        else
            executeCommand(sender, lbl, args);
        return true;
    }

    @SneakyThrows
    private void callEvent(Event event) {
        if (async)
            Bukkit.getScheduler().callSyncMethod(plugin, () -> {
                Bukkit.getPluginManager().callEvent(event);
                return null;
            }).get();
        else
            Bukkit.getPluginManager().callEvent(event);
    }

    @SneakyThrows
    public void executeCommand(CommandSender sender, String lbl, String[] args) {
        PlayerPreCommandEvent preCommandEvent;
        Player player = null;

        if (sender instanceof Player) {
            player = (Player) sender;
            preCommandEvent = new PlayerPreCommandEvent(player, this);
            callEvent(preCommandEvent);

        } else preCommandEvent = null;

        if (preCommandEvent == null || !preCommandEvent.isCancelled()) {
            boolean isSuccess = command(sender, lbl, args);
            if (player != null) {
                PlayerUseCommandEvent useCommandEvent = new PlayerUseCommandEvent(player, this, isSuccess);
                callEvent(useCommandEvent);
            }
        }
    }

    public abstract boolean command(CommandSender sender, String lbl, String[] args);

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
