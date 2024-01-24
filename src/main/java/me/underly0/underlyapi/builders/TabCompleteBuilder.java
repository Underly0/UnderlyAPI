package me.underly0.underlyapi.builders;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TabCompleteBuilder {
    public static TabCompleteBuilder Builder(CommandSender sender, String[] args) {
        return new TabCompleteBuilder(sender, args, SearchType.CONTAINS);
    }
    public static TabCompleteBuilder Builder(CommandSender sender, String[] args, SearchType type) {
        return new TabCompleteBuilder(sender, args, type);
    }

    private final CommandSender sender;
    private final String[] args;
    private final SearchType type;
    private List<String> complete;

    public TabCompleteBuilder addComplete(int index, List<String> complete) {
        if (args.length != index)
            return this;

        this.complete = filteredList(complete, getLastArg());
        return this;
    }

    public String getLastArg() {
        return args[args.length - 1];
    }

    public List<String> filteredList(List<String> lines, String lastArg) {
        List<String> filteredComplete = new ArrayList<>(complete);
        lines.removeIf(l ->
                type == SearchType.CONTAINS
                ? !l.contains(lastArg)
                : !l.startsWith(lastArg)
        );

        return filteredComplete;
    }

    public TabCompleteBuilder addComplete(int index, String... complete) {
        addComplete(index, Arrays.asList(complete));
        return this;
    }

    public List<String> build() {
        if (complete == null || complete.isEmpty())
            complete = filteredList(
                    Bukkit.getOnlinePlayers().stream()
                            .map(HumanEntity::getName)
                            .collect(Collectors.toList()),
                    getLastArg());

        return complete;
    }

    public enum SearchType {
        CONTAINS, STARTS_WITCH
    }
}
