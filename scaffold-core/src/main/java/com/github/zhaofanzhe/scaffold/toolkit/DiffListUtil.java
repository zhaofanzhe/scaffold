package com.github.zhaofanzhe.scaffold.toolkit;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiffListUtil {

    @FunctionalInterface
    public interface Equatable<T> {
        public boolean equal(T t1, T t2);
    }

    public record DiffResult<T>(List<T> addList, List<T> removeList) {

    }

    public static <T> DiffResult<T> diff(List<T> oldList, List<T> newList) {
        return diff(oldList, newList, Objects::equals);
    }

    public static <T> DiffResult<T> diff(List<T> oldList, List<T> newList, Equatable<T> eq) {
        return new DiffResult<>(moreThan(newList, oldList, eq), moreThan(oldList, newList, eq));
    }

    /**
     * t1 多于 t2
     */
    private static <T> List<T> moreThan(List<T> l1, List<T> l2, Equatable<T> eq) {
        List<T> list = new ArrayList<>();
        outer:
        for (T t1 : l1) {
            for (T t2 : l2) {
                if (eq.equal(t1, t2)) {
                    continue outer;
                }
            }
            list.add(t1);
        }
        return list;
    }

}
