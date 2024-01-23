package me.underly0.underlyapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationUtil {
    public static Vector stringToVector(String[] arg) {
        return new Vector(Double.parseDouble(arg[0]), Double.parseDouble(arg[1]),
                Double.parseDouble(arg[2]));
    }
    public static Location stringToLocation(String[] arg) {
        return new Location(Bukkit.getWorld(arg[0]), Double.parseDouble(arg[1]),
                Double.parseDouble(arg[2]), Double.parseDouble(arg[3]));
    }
    public static Location stringToLocationAndDir(String[] arg) {
        return new Location(Bukkit.getWorld(arg[0]), Double.parseDouble(arg[1]),
                Double.parseDouble(arg[2]), Double.parseDouble(arg[3]),
                Float.parseFloat(arg[4]), Float.parseFloat(arg[5]));
    }
}
