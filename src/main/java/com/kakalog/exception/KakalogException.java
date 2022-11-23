package com.kakalog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class KakalogException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();

    public KakalogException(String message) {
        super(message);
    }

    public KakalogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName,message);
    }
}
