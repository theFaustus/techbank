package com.evil.inc.account.command.infrastructure;

import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.events.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByAggregateId(AggregateId aggregateId);
}
