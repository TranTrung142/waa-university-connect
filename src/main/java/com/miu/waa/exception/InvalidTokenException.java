package com.miu.waa.exception;

import java.util.function.Supplier;
public class InvalidTokenException extends RuntimeException implements Supplier<InvalidTokenException> {
    public InvalidTokenException(String message) {
        super(message);
    }

    @Override
    public InvalidTokenException get() {
        return this;
    }
}
