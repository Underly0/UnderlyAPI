package me.underly0.underlyapi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTimeUtil {
    public static long parseTimeOnTicks(String input) {
        Pattern pattern = Pattern.compile("(\\d+)([tsmhdy])");
        Matcher matcher = pattern.matcher(input);

        return matcher.results()
                .mapToLong(match -> {
                    long value = Integer.parseInt(match.group(1));
                    char unit = match.group(2).charAt(0);

                    return value * getTicksByUnit(unit);
                })
                .sum();
    }
    public static long getTicksByUnit(char unit) {
        switch (unit) {
            case 's': return 20;
            case 'm': return 60 * 20;
            case 'h': return 60 * 60 * 20;
            case 'd': return 24 * 60 * 60 * 20;
            case 'y': return 365 * 24 * 60 * 60 * 20;
            case 't': return 1;
            default: throw new IllegalArgumentException();
        }
    }
}
