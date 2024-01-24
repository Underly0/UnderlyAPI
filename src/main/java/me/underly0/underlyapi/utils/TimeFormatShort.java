package me.underly0.underlyapi.utils;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeFormatShort {
    private final static String[] units = {"д.", "ч.", "м.", "с."};
    public static String formatTime(long seconds) {
        if (seconds == -1) return "Никогда";
        if (seconds == 0) return "0" + units[3];

        long[] time = {
                seconds / 86400,
                seconds / 3600 % 24,
                seconds / 60 % 60,
                seconds % 60
        };

        return IntStream.range(0, units.length)
                .filter(i -> time[i] > 0)
                .mapToObj(i -> time[i] + units[i])
                .collect(Collectors.joining(" "));
    }

}