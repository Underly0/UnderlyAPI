package me.underly0.underlyapi.utils;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeFormatFull {
    private static final String[][] units = {
            {"день", "дня", "дней"},
            {"час", "часа", "часов"},
            {"минуту", "минуты", "минут"},
            {"секунду", "секунды", "секунд"}
    };

    public static String formatTime(long seconds) {
        if (seconds == -1) return "Никогда";

        long[] time = {
                seconds / (24 * 3600),
                (seconds % (24 * 3600)) / 3600,
                (seconds % 3600) / 60,
                seconds % 60
        };

        return IntStream.range(0, units.length)
                .filter(i -> time[i] > 0)
                .mapToObj(i -> StringUtil.declensions(time[i], units[i]))
                .collect(Collectors.joining(" "));
    }
}
