package me.underly0.underlyapi.builders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.underly0.underlyapi.commons.pair.Pair;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

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
    private SimpleComplete complete;

    @SafeVarargs
    public final TabCompleteBuilder addComplete(int index, Pair<List<String>, String>... completes) {
        if (args.length != index)
            return this;

        this.complete = filteredList(List.of(completes), getLastArg());
        return this;
    }

    public TabCompleteBuilder addComplete(int index, List<String> completes) {
        addComplete(index, completes.stream().map(s -> Pair.of(s, (String) null)).toArray(Pair[]::new));
        return this;
    }

    public final TabCompleteBuilder addComplete(int index, CompleteType type) {
        addComplete(index, type, null);
        return this;
    }

    public final TabCompleteBuilder addComplete(int index, CompleteType type, String permission) {
        if (args.length != index)
            return this;

        this.complete = new SimpleComplete(type, null, permission);
        return this;
    }

    public String getLastArg() {
        return args[args.length - 1];
    }

    public SimpleComplete filteredList(List<Pair<List<String>, String>> lines, String lastArg) {
        List<String> filteredComplete = new ArrayList<>();

        lines.forEach(line -> filteredComplete.addAll(line.getFirst().stream()
                .filter(s -> (type == SearchType.CONTAINS ? s.contains(lastArg) : s.startsWith(lastArg)))
                .filter(s -> line.getSecond() == null || sender.hasPermission(line.getSecond()))
                .collect(Collectors.toList())));

        return filteredComplete.isEmpty()
                ? new SimpleComplete(CompleteType.NONE, emptyList(), null)
                : new SimpleComplete(CompleteType.CUSTOM, filteredComplete, null);
    }

    public List<String> getFilteredPlayers() {
        return filteredList(
                Bukkit.getOnlinePlayers().stream()
                        .map(s -> Pair.of(
                                singletonList(s.getName()),
                                complete.getPermission())
                        ).collect(Collectors.toList()),
                getLastArg()).getComplete();
    }

    @AllArgsConstructor
    @Getter
    public static class SimpleComplete {
        private final CompleteType type;
        private final List<String> complete;
        private final String permission;
    }

    public enum CompleteType {
        NONE, CUSTOM, PLAYERS
    }

    public List<String> build() {
        if (complete == null)
            return emptyList();

        switch (complete.getType()) {
            case CUSTOM:
            case NONE:
                return complete.getComplete();
            case PLAYERS:
                return getFilteredPlayers();
            default:
                return emptyList();
        }
    }

    public enum SearchType {
        CONTAINS, STARTS_WITH
    }
}
