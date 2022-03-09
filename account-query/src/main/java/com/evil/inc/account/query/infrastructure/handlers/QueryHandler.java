package com.evil.inc.account.query.infrastructure.handlers;

import com.evil.inc.account.query.api.queries.FindAccountByAccountHolderQuery;
import com.evil.inc.account.query.api.queries.FindAccountByIdQuery;
import com.evil.inc.account.query.api.queries.FindAllAccountsQuery;
import com.evil.inc.account.query.domain.BankAccount;
import com.evil.inc.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BankAccount> handle(FindAllAccountsQuery query);
    List<BankAccount> handle(FindAccountByIdQuery query);
    List<BankAccount> handle(FindAccountByAccountHolderQuery query);
}
