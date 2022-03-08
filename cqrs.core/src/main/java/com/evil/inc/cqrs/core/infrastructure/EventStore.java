package com.evil.inc.cqrs.core.infrastructure;

import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.events.Event;

import java.util.List;

public interface EventStore {
    void saveEvent(AggregateId aggregateId, Iterable<Event> events, long expectedVersion);
    List<Event> getEvents(AggregateId aggregateId);

}
