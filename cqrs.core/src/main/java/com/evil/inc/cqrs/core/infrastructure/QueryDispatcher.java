package com.evil.inc.cqrs.core.infrastructure;

import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.commands.CommandHandlerConsumer;
import com.evil.inc.cqrs.core.domain.BaseEntity;
import com.evil.inc.cqrs.core.queries.Query;
import com.evil.inc.cqrs.core.queries.QueryHandlerConsumer;

import java.util.List;

public interface QueryDispatcher<T extends Query, R extends BaseEntity> {
    <C extends T> void registerHandler(Class<C> type, QueryHandlerConsumer<T, R> handler);
    //Should refactor so won't return entities
    List<R> send(T query);
}
