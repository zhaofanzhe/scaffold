package com.github.zhaofanzhe.scaffold.exception;

public class NotFundResourceException extends Exception {

    public NotFundResourceException() {
    }

    public NotFundResourceException(String message) {
        super(message);
    }

    public NotFundResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFundResourceException(Throwable cause) {
        super(cause);
    }

    public NotFundResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
