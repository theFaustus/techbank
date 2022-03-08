package com.evil.inc.cqrs.core.exceptions;

import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.events.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventStoreConcurrencyException extends RuntimeException {
    private final AggregateId aggregateId;
    private final Class<?> aggregateType;
    private final long originalVersion;

    public EventStoreConcurrencyException(
            AggregateId aggregateId,
            Class<?> aggregateType,
            long originalVersion
    ) {
        super("EventStoreConcurrencyException: " + aggregateType.getSimpleName() + ", id: " + aggregateId + ", original version:  " + originalVersion + ".");

        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.originalVersion = originalVersion;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    public Class<?> getAggregateClazz() {
        return aggregateType;
    }

    public long getOriginalVersion() {
        return originalVersion;
    }

}
