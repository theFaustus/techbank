package com.evil.inc.account.command.infrastructure.handlers;

import com.evil.inc.account.command.api.commands.CloseAccountCommand;
import com.evil.inc.account.command.api.commands.DepositFundsCommand;
import com.evil.inc.account.command.api.commands.OpenAccountCommand;
import com.evil.inc.account.command.api.commands.RestoreDbCommand;
import com.evil.inc.account.command.api.commands.WithdrawFundsCommand;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithdrawFundsCommand command);
    void handle(CloseAccountCommand command);
    void handle(RestoreDbCommand command);
}
