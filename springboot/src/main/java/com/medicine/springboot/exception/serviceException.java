package com.medicine.springboot.exception;

import lombok.Getter;

@Getter
public class serviceException extends RuntimeException {

    private final String code;

    public serviceException (String message) {
        super(message);
        this.code = "438";
    }

    public serviceException (String code, String message) {
        super(message);
        this.code = code;
    }
}
