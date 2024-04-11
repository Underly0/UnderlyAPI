package me.underly0.underlyapi.common.object;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import me.underly0.underlyapi.util.LocationUtil;
import org.bukkit.World;
import org.bukkit.util.Vector;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class Points {
    World world;
    Vector min, max;

    private Points(World world, Vector min, Vector max) {
        this.world = world;
        this.min = min;
        this.max = max;
    }

    public static Points of(World world, Vector point1, Vector point2) {
        Vector min = LocationUtil.getMinVector(point1, point2);
        Vector max = LocationUtil.getMaxVector(point1, point2);

        return new Points(world, min, max);
    }
}
