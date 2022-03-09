package com.evil.inc.account.command.infrastructure;

import com.evil.inc.account.command.domain.AccountAggregate;
import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.events.Event;
import com.evil.inc.cqrs.core.events.EventModel;
import com.evil.inc.cqrs.core.exceptions.EventStoreConcurrencyException;
import com.evil.inc.cqrs.core.exceptions.InvalidAggregateException;
import com.evil.inc.cqrs.core.infrastructure.EventStore;
import com.evil.inc.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    @Override
    public void saveEvent(AggregateId aggregateId, Iterable<Event> events, long expectedVersion) {
        final List<EventModel> byAggregateId = eventStoreRepository.findByAggregateId(aggregateId);
        if (expectedVersion != -1 && byAggregateId.get(byAggregateId.size() - 1).getVersion() != expectedVersion) {
            throw new EventStoreConcurrencyException(aggregateId, AccountAggregate.class, expectedVersion);
        }
        for (var event : events) {
            var eventModel = toEventModel(event, ++expectedVersion);
            EventModel persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    private EventModel toEventModel(Event event, long version) {
        event.setVersion(version);
        return EventModel.builder()
                .systemCaptureDateTime(event.getSystemCaptureDateTime())
                .aggregateId(event.getAggregateId())
                .aggregateType(AccountAggregate.class.getTypeName())
                .version(version)
                .eventType(event.getType())
                .event(event)
                .build();
    }

    @Override
    public List<Event> getEvents(AggregateId aggregateId) {
        final List<EventModel> events = eventStoreRepository.findByAggregateId(aggregateId);
        if (events == null || events.isEmpty()) {
            throw new InvalidAggregateException("Invalid account id provided.");
        }
        return events.stream().map(EventModel::getEvent).collect(Collectors.toList());
    }

    @Override
    public List<AggregateId> getAggregateIds() {
        return eventStoreRepository.findAll().stream().map(EventModel::getAggregateId).collect(Collectors.toList());
    }
}
