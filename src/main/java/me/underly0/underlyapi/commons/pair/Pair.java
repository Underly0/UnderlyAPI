package me.underly0.underlyapi.commons.pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Pair<F, S> {
    private final F first;
    private final S second;
    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }
}
