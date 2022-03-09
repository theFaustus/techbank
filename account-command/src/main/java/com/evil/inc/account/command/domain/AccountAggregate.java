package com.evil.inc.account.command.domain;

import com.evil.inc.account.command.api.commands.CloseAccountCommand;
import com.evil.inc.account.command.api.commands.DepositFundsCommand;
import com.evil.inc.account.command.api.commands.OpenAccountCommand;
import com.evil.inc.account.command.api.commands.WithdrawFundsCommand;
import com.evil.inc.account.common.events.AccountOpenedEvent;
import com.evil.inc.account.common.events.AccountClosedEvent;
import com.evil.inc.account.common.events.FundsDepositedEvent;
import com.evil.inc.account.common.events.FundsWithdrawnEvent;
import com.evil.inc.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        applyNewChange(AccountOpenedEvent.builder()
                .aggregateId(command.getAggregateId())
                .accountHolder(command.getAccountHolder())
                .creationDate(LocalDateTime.now())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public double getBalance() {
        return balance;
    }

    public void depositFunds(DepositFundsCommand command) {
        if (!active) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        }
        if (command.getAmount() <= 0) {
            throw new IllegalStateException("The deposit amount must be greater than 0!");
        }
        applyNewChange(FundsDepositedEvent.builder()
                .aggregateId(this.id)
                .amount(command.getAmount())
                .build());
    }

    public void withdrawFunds(WithdrawFundsCommand command) {
        if (!active) {
            throw new IllegalStateException("Funds cannot be withdrawn from a closed account!");
        }
        if (command.getAmount() > balance) {
            throw new IllegalStateException("Funds cannot be withdrawn. Insufficient funds on the balance!");
        }
        applyNewChange(FundsWithdrawnEvent.builder()
                .aggregateId(this.id)
                .amount(command.getAmount())
                .build());
    }

    public void closeAccount(CloseAccountCommand command) {
        if (!active) {
            throw new IllegalStateException("Cannot close an already closed account!");
        }
        applyNewChange(AccountClosedEvent.builder().aggregateId(this.id).build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getAggregateId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getAggregateId();
        this.balance += event.getAmount();
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getAggregateId();
        this.balance -= event.getAmount();
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getAggregateId();
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }
}
