package com.monthly.statementprocessor.util;

import java.util.function.Function;

import com.monthly.statementprocessor.exception.StatementProcessException;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    static <T, R, E extends Exception> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                throw new StatementProcessException(e);
            }
        };
    }

    R apply(T t) throws E;
}