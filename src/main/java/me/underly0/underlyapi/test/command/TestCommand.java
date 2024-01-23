package me.underly0.underlyapi.test.command;

import me.underly0.underlyapi.builders.TabCompleteBuilder;
import me.underly0.underlyapi.commons.command.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestCommand extends CommandBase {
    public TestCommand(Plugin plugin, String command, String... aliases) {
        super(plugin, command, aliases);
    }

    @Override
    public void command(CommandSender sender, String lbl, String[] args) {
        sender.sendMessage("test");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return TabCompleteBuilder.Builder(sender, args)
                .addComplete(1, Arrays.asList("1231", "54", "Никита", "Овца"))
                .addComplete(2, Collections.emptyList())
                .addComplete(3, Arrays.asList("как", "где"))
                .build();
    }


}
