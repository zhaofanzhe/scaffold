package com.github.zhaofanzhe.scaffold.tuples;

public class Pair<A, B> extends Unit<A> {

    private B value1;

    public Pair(A value0, B value1) {
        super(value0);
        this.value1 = value1;
    }

    public void setValue1(B value1) {
        this.value1 = value1;
    }

    public B getValue1() {
        return value1;
    }

}
