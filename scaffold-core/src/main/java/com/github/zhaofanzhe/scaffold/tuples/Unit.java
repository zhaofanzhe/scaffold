package com.github.zhaofanzhe.scaffold.tuples;

public class Unit<A> implements Tuple {

    private A value0;

    public Unit(A value0) {
        this.value0 = value0;
    }

    public void setValue0(A value0) {
        this.value0 = value0;
    }

    public A getValue0() {
        return value0;
    }

}
