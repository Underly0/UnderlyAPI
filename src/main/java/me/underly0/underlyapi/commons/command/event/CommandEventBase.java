package me.underly0.underlyapi.commons.command.event;


import lombok.*;
import me.underly0.underlyapi.api.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
public abstract class CommandEventBase extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Command command;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}