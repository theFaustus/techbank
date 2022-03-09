package com.evil.inc.account.command.config;

import com.evil.inc.account.command.api.commands.CloseAccountCommand;
import com.evil.inc.account.command.api.commands.DepositFundsCommand;
import com.evil.inc.account.command.api.commands.OpenAccountCommand;
import com.evil.inc.account.command.api.commands.RestoreDbCommand;
import com.evil.inc.account.command.api.commands.WithdrawFundsCommand;
import com.evil.inc.account.command.infrastructure.handlers.CommandHandler;
import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class EventSourcingConfiguration {

    private final CommandDispatcher<Command> commandDispatcher;
    private final CommandHandler commandHandler;


    @PostConstruct
    public void registerHandlers(){
        commandDispatcher.registerHandler(OpenAccountCommand.class, command -> commandHandler.handle((OpenAccountCommand) command));
        commandDispatcher.registerHandler(DepositFundsCommand.class, command -> commandHandler.handle((DepositFundsCommand) command));
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, command -> commandHandler.handle((WithdrawFundsCommand) command));
        commandDispatcher.registerHandler(CloseAccountCommand.class, command -> commandHandler.handle((CloseAccountCommand) command));
        commandDispatcher.registerHandler(RestoreDbCommand.class, command -> commandHandler.handle((RestoreDbCommand) command));
    }
}
