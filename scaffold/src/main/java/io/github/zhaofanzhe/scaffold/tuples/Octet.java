package io.github.zhaofanzhe.scaffold.tuples;

public class Octet<A, B, C, D, E, F, G,H> extends Septet<A, B, C, D, E, F, G>{

    private H value7;

    public Octet(A value0, B value1, C value2, D value3, E value4, F value5, G value6, H value7) {
        super(value0, value1, value2, value3, value4, value5, value6);
        this.value7 = value7;
    }

    public void setValue7(H value7) {
        this.value7 = value7;
    }

    public H getValue7() {
        return value7;
    }

}
