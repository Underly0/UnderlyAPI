package me.underly0.underlyapi.common.object;

@FunctionalInterface
public interface MultiFunction<A, B, R> {
    R apply(A s, B a);
}
