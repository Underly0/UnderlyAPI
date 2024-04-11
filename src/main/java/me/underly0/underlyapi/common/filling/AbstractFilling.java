package me.underly0.underlyapi.common.filling;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import me.underly0.underlyapi.common.object.Points;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public abstract class AbstractFilling {
    World world;
    Vector min;
    Vector max;

    int minX, minY, minZ;
    int maxX, maxY, maxZ;
    protected AbstractFilling(Points points) {
        this.world = points.getWorld();
        this.min = points.getMin();
        this.max = points.getMax();

        this.minX = min.getBlockX();
        this.minY = min.getBlockY();
        this.minZ = min.getBlockZ();

        this.maxX = max.getBlockX();
        this.maxY = max.getBlockY();
        this.maxZ = max.getBlockZ();

    }

    public abstract void start(Consumer<Location> consumer);
}
