package me.underly0.underlyapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringUtil {

    public static String color(String text) {
        int version = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);
        if (version >= 16)
            text = text.replaceAll("#([0-9a-f]{6})", "&x&$1");

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> replaceObjects(List<String> list, Object... replace) {
        if (replace.length % 2 != 0)
            throw new IllegalArgumentException();

        return list.stream().map(line -> {
            for (int i = 0; i < replace.length; i += 2)
                line = line.replace(String.valueOf(replace[i]), String.valueOf(replace[i + 1]));
            return StringUtil.color(line);
        }).collect(Collectors.toList());
    }

    public static List<String> mapList(List<String> list, Function<String, String> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    public static String declensions(long point, String[] units) {
        point = Math.abs(point);
        long last = point % 100;

        if (last > 10 && last < 21)
            return point + " " + units[2];

        last = point % 10;
        String result =
                (last == 0 || last > 4)
                        ? units[2]
                        : (last == 1)
                        ? units[0]
                        : units[1];

        return point + " " + result.trim();
    }

    public static String declensions(long point, String value1, String value2, String value3) {
        return declensions(point, Arrays.asList(value1, value2, value3).toArray(new String[0]));
    }

    public static final Map<String, double[]> COLOR_LIST
            = new HashMap<>() {{
        put("0", new double[]{1.0E-4, 1.0E-4, 1.0E-4});
        put("1", new double[]{1.0E-4, 1.0E-4, 1.0});
        put("2", new double[]{0.1333, 0.545, 0.1333});
        put("3", new double[]{0.1255, 0.698, 0.6666});
        put("4", new double[]{1.0, 1.0E-4, 1.0E-4});
        put("5", new double[]{0.5804, 1.0E-4, 0.8274});
        put("6", new double[]{1.0, 0.8431, 1.0E-4});
        put("7", new double[]{0.8118, 0.8118, 0.8118});
        put("8", new double[]{0.4118, 0.4118, 0.4118});
        put("9", new double[]{0.4118, 1.0, 1.0});
        put("a", new double[]{1.0E-4, 1.0, 1.0E-4});
        put("b", new double[]{1.0E-4, 1.0, 1.0});
        put("c", new double[]{0.8039, 0.3608, 0.3608});
        put("d", new double[]{1.0, 0.4118, 0.7059});
        put("e", new double[]{1.0, 1.0, 1.0E-4});
        put("f", new double[]{1.0, 1.0, 1.0});
    }};

}
