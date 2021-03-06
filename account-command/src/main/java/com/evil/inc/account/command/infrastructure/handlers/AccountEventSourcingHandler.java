package com.evil.inc.account.command.infrastructure.handlers;

import com.evil.inc.account.command.domain.AccountAggregate;
import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.domain.AggregateRoot;
import com.evil.inc.cqrs.core.events.Event;
import com.evil.inc.cqrs.core.exceptions.InvalidAggregateVersionException;
import com.evil.inc.cqrs.core.handlers.EventSourcingHandler;
import com.evil.inc.cqrs.core.infrastructure.EventStore;
import com.evil.inc.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;
    private final EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvent(aggregateRoot.getAggregateId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(AggregateId aggregateId) {
        final AccountAggregate accountAggregate = new AccountAggregate();
        final List<Event> events = eventStore.getEvents(aggregateId);
        if (events != null && !events.isEmpty()) {
            accountAggregate.replayEvents(events);
            final long latestVersion = events.stream()
                    .map(Event::getVersion)
                    .max(Comparator.naturalOrder())
                    .orElseThrow(() -> new InvalidAggregateVersionException("Invalid version on the aggregate " + aggregateId));
            accountAggregate.setVersion(latestVersion);
        }
        return accountAggregate;
    }

    @Override
    public void republishEvents() {
        final List<AggregateId> aggregateIds = eventStore.getAggregateIds();
        for (AggregateId aggregateId : aggregateIds) {
            final AccountAggregate accountAggregate = getById(aggregateId);
            if(accountAggregate == null || !accountAggregate.isActive()) continue;
            final List<Event> events = eventStore.getEvents(aggregateId);
            for (Event event : events) {
                eventProducer.produce(accountAggregate.getClass().getSimpleName(), event);
            }
        }
    }
}
