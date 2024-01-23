package me.underly0.underlyapi.utils;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeFormatShort {
    private final static String[] units = {"д.", "ч.", "м.", "с."};
    public static String formatTime(long s) {
        if (s == -1) return "Никогда";

        long[] time = {
                s / 86400,
                s / 3600 % 24,
                s / 60 % 60,
                s % 60
        };

        return IntStream.range(0, units.length)
                .mapToObj(i -> time[i] + units[i])
                .collect(Collectors.joining(" "));
    }

}