package me.underly0.underlyapi.test.event;

import me.underly0.underlyapi.commons.command.event.list.PlayerPreCommandEvent;
import me.underly0.underlyapi.commons.command.event.list.PlayerUseCommandEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TestEvent implements Listener {
    @EventHandler
    public void onPreCommand(PlayerPreCommandEvent e) {
    }

    @EventHandler
    public void onUseCommand(PlayerUseCommandEvent e) {
    }

}
