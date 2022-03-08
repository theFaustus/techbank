package com.evil.inc.account.command.infrastructure;

import com.evil.inc.account.command.domain.AccountAggregate;
import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.domain.AggregateRoot;
import com.evil.inc.cqrs.core.events.Event;
import com.evil.inc.cqrs.core.exceptions.InvalidAggregateVersionException;
import com.evil.inc.cqrs.core.handlers.EventSourcingHandler;
import com.evil.inc.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;

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
}
