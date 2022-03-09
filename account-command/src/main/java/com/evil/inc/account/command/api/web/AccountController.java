package com.evil.inc.account.command.api.web;

import com.evil.inc.account.command.api.commands.CloseAccountCommand;
import com.evil.inc.account.command.api.commands.DepositFundsCommand;
import com.evil.inc.account.command.api.commands.OpenAccountCommand;
import com.evil.inc.account.command.api.commands.RestoreDbCommand;
import com.evil.inc.account.command.api.commands.WithdrawFundsCommand;
import com.evil.inc.account.common.dto.Response;
import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final CommandDispatcher<Command> commandDispatcher;

    @PostMapping
    public ResponseEntity<Response> openAccount(@RequestBody OpenAccountCommand command){
        command.setAggregateId(AggregateId.from(UUID.randomUUID().toString()));
        commandDispatcher.dispatch(command);
        return ResponseEntity.ok(new Response("Bank account [" + command.getAggregateId() + "] created"));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Response> depositFunds(@RequestBody DepositFundsCommand command){
        commandDispatcher.dispatch(command);
        return ResponseEntity.ok(new Response("Successfully deposited " + command.getAmount() + " to account [" + command.getAggregateId() + "]"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Response> withdrawFunds(@RequestBody WithdrawFundsCommand command){
        commandDispatcher.dispatch(command);
        return ResponseEntity.ok(new Response("Successfully withdrawn " + command.getAmount() + " from account [" + command.getAggregateId() + "]"));
    }

    @PostMapping("/republish")
    public ResponseEntity<Response> republishEvents(){
        commandDispatcher.dispatch(new RestoreDbCommand());
        return ResponseEntity.ok(new Response("Restore of the DB started successfully"));
    }

    @DeleteMapping
    public ResponseEntity<Response> closeAccount(@RequestBody CloseAccountCommand command){
        commandDispatcher.dispatch(command);
        return ResponseEntity.ok(new Response("Bank account [" + command.getAggregateId() + "] closed"));
    }

}
