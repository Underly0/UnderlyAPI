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
        return new TabCompleteBuilder(sender, args);
    }
    private final CommandSender sender;
    private final String[] args;
    private List<String> complete;

    public TabCompleteBuilder addComplete(int index, List<String> complete) {
        if (args.length != index)
            return this;

        String lastArg = args[args.length - 1];
        if (complete.isEmpty())
            complete = Bukkit.getOnlinePlayers().stream()
                    .map(HumanEntity::getName)
                    .collect(Collectors.toList());
        List<String> filteredComplete = new ArrayList<>(complete);

        filteredComplete.removeIf(l -> !l.contains(lastArg));

        this.complete = filteredComplete;
        return this;
    }
    public TabCompleteBuilder addComplete(int index, String... complete) {
        addComplete(index, Arrays.asList(complete));
        return this;
    }

    public List<String> build() {
        return complete;
    }

}
