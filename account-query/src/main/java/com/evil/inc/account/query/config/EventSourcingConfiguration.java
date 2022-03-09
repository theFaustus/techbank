package com.evil.inc.account.query.config;

import com.evil.inc.account.query.api.queries.FindAccountByAccountHolderQuery;
import com.evil.inc.account.query.api.queries.FindAccountByIdQuery;
import com.evil.inc.account.query.api.queries.FindAllAccountsQuery;
import com.evil.inc.account.query.domain.BankAccount;
import com.evil.inc.account.query.infrastructure.handlers.QueryHandler;
import com.evil.inc.cqrs.core.infrastructure.QueryDispatcher;
import com.evil.inc.cqrs.core.queries.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class EventSourcingConfiguration {

    private final QueryDispatcher<Query, BankAccount> queryDispatcher;
    private final QueryHandler queryHandler;


    @PostConstruct
    public void registerHandlers(){
        queryDispatcher.registerHandler(FindAccountByIdQuery.class, query -> queryHandler.handle((FindAccountByIdQuery) query));
        queryDispatcher.registerHandler(FindAccountByAccountHolderQuery.class, query -> queryHandler.handle((FindAccountByAccountHolderQuery) query));
        queryDispatcher.registerHandler(FindAllAccountsQuery.class, query -> queryHandler.handle((FindAllAccountsQuery) query));
    }
}
