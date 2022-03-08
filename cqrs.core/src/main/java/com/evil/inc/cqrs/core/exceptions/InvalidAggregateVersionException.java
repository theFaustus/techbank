package com.evil.inc.cqrs.core.exceptions;

public class InvalidAggregateVersionException extends RuntimeException{
    public InvalidAggregateVersionException(String s) {
        super(s);
    }
}
