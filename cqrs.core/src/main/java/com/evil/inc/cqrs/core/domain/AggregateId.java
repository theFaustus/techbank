package com.evil.inc.cqrs.core.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class AggregateId extends ValueObject {

    private final String id;

    public static AggregateId generate() {
        return new AggregateId(UUID.randomUUID().toString());
    }

    @JsonCreator
    public static AggregateId from(String aggregateId) {
        return new AggregateId(aggregateId);
    }

    private AggregateId(String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof AggregateId
                && id.equals(((AggregateId) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }

}
