package me.underly0.underlyapi.impl.inventory;

import me.underly0.underlyapi.utils.ReflectionUtil;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FakeTitle {

    public void send(Inventory inventory, String newTitle) {
        inventory.getViewers().forEach(h -> {
                try {

                    Object craftPlayer = Class.forName(String.format("org.bukkit.craftbukkit.%s.entity.", ReflectionUtil.getVersion()))
                            .getMethod("getHandle").invoke(h);

                    Class<?> entityPlayerClass = craftPlayer.getClass();

                    // Accessing the activeContainer field
                    Field activeContainerField = entityPlayerClass.getDeclaredField("activeContainer");
                    activeContainerField.setAccessible(true);
                    Object activeContainer = activeContainerField.get(craftPlayer);

                    // Accessing the windowId and getType methods
                    Method getWindowIdMethod = activeContainer.getClass().getMethod("getWindowId");
                    Method getTypeMethod = activeContainer.getClass().getMethod("getType");

                    int windowId = (int) getWindowIdMethod.invoke(activeContainer);
                    Object containerType = getTypeMethod.invoke(activeContainer);

                    // Accessing CraftChatMessage.fromStringOrNull method
                    Class<?> craftChatMessageClass = ReflectionUtil.getNMSClass("IChatBaseComponent$ChatSerializer");
                    Method fromStringOrNullMethod = craftChatMessageClass.getMethod("a", String.class);

                    Object titleComponent = fromStringOrNullMethod.invoke(null, "{\"text\":\"" + newTitle + "\"}");

                    // Creating PacketPlayOutOpenWindow instance
                    Class<?> packetOpenWindowClass = ReflectionUtil.getNMSClass("PacketPlayOutOpenWindow");
                    Constructor<?> packetOpenWindowConstructor = packetOpenWindowClass.getConstructor(int.class, ReflectionUtil.getNMSClass("Containers"), ReflectionUtil.getNMSClass("IChatBaseComponent"));
                    Object packet = packetOpenWindowConstructor.newInstance(windowId, containerType, titleComponent);

                    // Accessing playerConnection and sending packet
                    Field playerConnectionField = entityPlayerClass.getField("playerConnection");
                    Object playerConnection = playerConnectionField.get(craftPlayer);
                    Method sendPacketMethod = playerConnection.getClass().getMethod("sendPacket", ReflectionUtil.getNMSClass("Packet"));
                    sendPacketMethod.invoke(playerConnection, packet);

                    // Updating inventory
                    Method updateInventoryMethod = craftPlayer.getClass().getMethod("updateInventory", ReflectionUtil.getNMSClass("Container"));
                    updateInventoryMethod.invoke(craftPlayer, activeContainer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }
}
