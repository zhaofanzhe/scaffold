package com.github.zhaofanzhe.scaffold.lottery;

@SuppressWarnings("ClassCanBeRecord")
public class LotteryConfigure<T> {

    public static <T> LotteryConfigure<T> configure(T payload, long quantity) {
        return new LotteryConfigure<T>(payload, quantity);
    }

    private final T payload;

    private final long quantity;

    public LotteryConfigure(T payload, long quantity) {
        this.payload = payload;
        this.quantity = quantity;
    }

    public T getPayload() {
        return payload;
    }

    public long getQuantity() {
        return quantity;
    }

}
