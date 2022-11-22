package com.kakalog.exception;

public abstract class KakalogException extends RuntimeException{

    public KakalogException(String message) {
        super(message);
    }

    public KakalogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
