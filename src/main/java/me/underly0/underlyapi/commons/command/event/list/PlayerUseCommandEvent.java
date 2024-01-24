package me.underly0.underlyapi.commons.command.event.list;

import lombok.Getter;
import me.underly0.underlyapi.api.command.Command;
import me.underly0.underlyapi.commons.command.event.CommandEventBase;
import org.bukkit.entity.Player;

@Getter
public class PlayerUseCommandEvent extends CommandEventBase {
    private final boolean success;
    public PlayerUseCommandEvent(Player player, Command command, boolean success) {
        super(player, command);
        this.success = success;
    }
}
