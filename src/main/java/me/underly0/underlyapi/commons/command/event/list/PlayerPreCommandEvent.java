package me.underly0.underlyapi.commons.command.event.list;

import lombok.Getter;
import lombok.Setter;
import me.underly0.underlyapi.api.command.Command;
import me.underly0.underlyapi.commons.command.event.CommandEventBase;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Setter
@Getter
public class PlayerPreCommandEvent extends CommandEventBase implements Cancellable {
    private boolean cancelled = false;
    public PlayerPreCommandEvent(Player player, Command command) {
        super(player, command);
    }
}
