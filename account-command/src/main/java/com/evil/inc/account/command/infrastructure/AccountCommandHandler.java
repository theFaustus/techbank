package com.evil.inc.account.command.infrastructure;

import com.evil.inc.account.command.api.commands.CloseAccountCommand;
import com.evil.inc.account.command.api.commands.DepositFundsCommand;
import com.evil.inc.account.command.api.commands.OpenAccountCommand;
import com.evil.inc.account.command.api.commands.WithdrawFundsCommand;
import com.evil.inc.account.command.domain.AccountAggregate;
import com.evil.inc.cqrs.core.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AccountCommandHandler implements CommandHandler {
    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        final AccountAggregate aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        final AccountAggregate aggregate = eventSourcingHandler.getById(command.getAggregateId());
        aggregate.depositFunds(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        final AccountAggregate aggregate = eventSourcingHandler.getById(command.getAggregateId());
        aggregate.withdrawFunds(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        final AccountAggregate aggregate = eventSourcingHandler.getById(command.getAggregateId());
        aggregate.closeAccount(command);
        eventSourcingHandler.save(aggregate);
    }
}
