package com.github.zhaofanzhe.scaffold.exception;

import java.io.Serial;

public class BusinessInterruptionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -817995709427418283L;

    private final Object payload;

    public BusinessInterruptionException(Object payload) {
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }

}
