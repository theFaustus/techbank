package com.evil.inc.account.query.infrastructure;

import com.evil.inc.account.query.domain.BankAccount;
import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.commands.CommandHandlerConsumer;
import com.evil.inc.cqrs.core.domain.BaseEntity;
import com.evil.inc.cqrs.core.infrastructure.QueryDispatcher;
import com.evil.inc.cqrs.core.queries.Query;
import com.evil.inc.cqrs.core.queries.QueryHandlerConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
class AccountQueryDispatcher implements QueryDispatcher<Query, BankAccount> {
    private final Map<Class<? extends Query>, QueryHandlerConsumer<Query, BankAccount>> routes = new HashMap<>();


    @Override
    public <C extends Query> void registerHandler(Class<C> type, QueryHandlerConsumer<Query, BankAccount> handler) {
        routes.put(type, handler);
    }

    @Override
    public List<BankAccount> send(Query query) {
        var handler = routes.get(query.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler registered for " + query.getClass());
        }
        return handler.accept(query);
    }
}
