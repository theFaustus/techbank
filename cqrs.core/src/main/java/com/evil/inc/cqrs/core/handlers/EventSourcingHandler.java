package com.evil.inc.cqrs.core.handlers;

import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);
    T getById(AggregateId aggregateId);
    void republishEvents();
}
