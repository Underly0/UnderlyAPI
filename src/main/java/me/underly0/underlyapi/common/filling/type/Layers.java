package me.underly0.underlyapi.common.filling.type;

import me.underly0.underlyapi.common.filling.AbstractFilling;
import me.underly0.underlyapi.common.object.Points;
import org.bukkit.Location;
import org.bukkit.util.Consumer;

public class Layers extends AbstractFilling {
    public Layers(Points points) {
        super(points);
    }


    @Override
    public void start(Consumer<Location> consumer) {

        for (int y = super.getMinY(); y <= super.getMaxY(); y++) {
            for (int x = super.getMinX(); x <= super.getMaxX(); x++) {
                for (int z = super.getMinZ(); z <= super.getMaxZ(); z++) {

                    Location location = new Location(super.getPoints().getWorld(), x, y, z);
                    consumer.accept(location);
                }
            }
        }

    }
}
