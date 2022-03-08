package com.evil.inc.cqrs.core.exceptions;

public class InvalidAggregateException extends RuntimeException {
    public InvalidAggregateException(String s) {
        super(s);
    }
}
