package io.github.zhaofanzhe.scaffold.tuples;

public class Ennead<A, B, C, D, E, F, G,H,I> extends Octet<A, B, C, D, E, F, G,H>{

    private I value8;

    public Ennead(A value0, B value1, C value2, D value3, E value4, F value5, G value6, H value7, I value8) {
        super(value0, value1, value2, value3, value4, value5, value6, value7);
        this.value8 = value8;
    }

    public void setValue8(I value8) {
        this.value8 = value8;
    }

    public I getValue8() {
        return value8;
    }

}
