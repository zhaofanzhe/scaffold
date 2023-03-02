package io.github.zhaofanzhe.scaffold.tuples;

public class Tuples {

    public static <A> Unit<A> of(A value0) {
        return new Unit<>(value0);
    }

    public static <A, B> Pair<A, B> of(A value0, B value1) {
        return new Pair<>(value0, value1);
    }

    public static <A, B, C> Triplet<A, B, C> of(A value0, B value1, C value2) {
        return new Triplet<>(value0, value1, value2);
    }

    public static <A, B, C, D> Quartet<A, B, C, D> of(A value0, B value1, C value2, D value3) {
        return new Quartet<>(value0, value1, value2, value3);
    }

    public static <A, B, C, D, E> Quintet<A, B, C, D, E> of(A value0, B value1, C value2, D value3, E value4) {
        return new Quintet<>(value0, value1, value2, value3, value4);
    }

    public static <A, B, C, D, E, F> Sextet<A, B, C, D, E, F> of(A value0, B value1, C value2, D value3, E value4, F value5) {
        return new Sextet<>(value0, value1, value2, value3, value4, value5);
    }

    public static <A, B, C, D, E, F, G> Septet<A, B, C, D, E, F, G> of(A value0, B value1, C value2, D value3, E value4, F value5, G value6) {
        return new Septet<>(value0, value1, value2, value3, value4, value5, value6);
    }

    public static <A, B, C, D, E, F, G, H> Octet<A, B, C, D, E, F, G, H> of(A value0, B value1, C value2, D value3, E value4, F value5, G value6, H value7) {
        return new Octet<>(value0, value1, value2, value3, value4, value5, value6, value7);
    }

    public static <A, B, C, D, E, F, G, H, I> Ennead<A, B, C, D, E, F, G, H, I> of(A value0, B value1, C value2, D value3, E value4, F value5, G value6, H value7, I value8) {
        return new Ennead<>(value0, value1, value2, value3, value4, value5, value6, value7, value8);
    }

    public static <A, B, C, D, E, F, G, H, I, J> Decade<A, B, C, D, E, F, G, H, I, J> of(A value0, B value1, C value2, D value3, E value4, F value5, G value6, H value7, I value8, J value9) {
        return new Decade<>(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9);
    }

}
