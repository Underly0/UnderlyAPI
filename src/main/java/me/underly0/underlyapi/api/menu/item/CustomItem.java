package me.underly0.underlyapi.api.menu.item;

import me.underly0.underlyapi.api.menu.action.MenuAction;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CustomItem {
    ItemStack getItem();

    MenuAction getAction();

    List<Integer> getSlots();

    void setSlots(int... slots);

    void setSlots(List<Integer> slots);
}
