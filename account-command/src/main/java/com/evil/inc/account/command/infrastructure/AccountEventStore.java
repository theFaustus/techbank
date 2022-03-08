package com.evil.inc.account.command.infrastructure;

import com.evil.inc.account.command.domain.AccountAggregate;
import com.evil.inc.account.command.domain.EventStoreRepository;
import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.events.Event;
import com.evil.inc.cqrs.core.events.EventModel;
import com.evil.inc.cqrs.core.exceptions.EventStoreConcurrencyException;
import com.evil.inc.cqrs.core.exceptions.InvalidAggregateException;
import com.evil.inc.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvent(AggregateId aggregateId, Iterable<Event> events, long expectedVersion) {
        for (var event : events) {
            var eventModel = toEventModel(event);
            EventModel persistedEvent = null;
            try {
                persistedEvent = eventStoreRepository.save(eventModel);
            } catch (OptimisticLockingFailureException e) {
                handleSQLException(aggregateId, expectedVersion);
            }
            if (persistedEvent != null) {
                //TODO: produce event to Kafka
            }
        }
    }

    private void handleSQLException(AggregateId aggregateId, long expectedVersion) {
        throw new EventStoreConcurrencyException(aggregateId, AccountAggregate.class, expectedVersion);
    }

    private EventModel toEventModel(Event event) {
        return EventModel.builder()
                .systemCaptureDateTime(event.getSystemCaptureDateTime())
                .aggregateId(event.getId())
                .aggregateType(AccountAggregate.class.getTypeName())
                .version(event.getVersion())
                .eventType(event.getType())
                .event(event)
                .build();
    }

    @Override
    public List<Event> getEvents(AggregateId aggregateId) {
        final List<EventModel> events = eventStoreRepository.findByAggregateId(aggregateId);
        if(events == null || events.isEmpty()){
            throw new InvalidAggregateException("Invalid account id provided.");
        }
        return events.stream().map(EventModel::getEvent).collect(Collectors.toList());
    }
}