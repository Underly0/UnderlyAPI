package me.underly0.underlyapi.common.command.event.list;

import lombok.Getter;
import me.underly0.underlyapi.api.command.Command;
import me.underly0.underlyapi.common.command.event.AbstractCommandEvent;
import org.bukkit.entity.Player;

@Getter
public class PlayerUseCommandEvent extends AbstractCommandEvent {
    private final boolean success;
    public PlayerUseCommandEvent(Player player, Command command, boolean success) {
        super(player, command);
        this.success = success;
    }
}
