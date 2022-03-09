package com.evil.inc.account.query.api.web;

import com.evil.inc.account.query.api.dto.AccountResponse;
import com.evil.inc.account.query.api.queries.FindAccountByAccountHolderQuery;
import com.evil.inc.account.query.api.queries.FindAccountByIdQuery;
import com.evil.inc.account.query.api.queries.FindAllAccountsQuery;
import com.evil.inc.account.query.domain.BankAccount;
import com.evil.inc.cqrs.core.infrastructure.QueryDispatcher;
import com.evil.inc.cqrs.core.queries.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class LookupAccountController {

    private final QueryDispatcher<Query, BankAccount> queryDispatcher;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(){
        final List<BankAccount> baseEntities = queryDispatcher.send(new FindAllAccountsQuery());
        final List<AccountResponse> response = baseEntities.stream().map(AccountResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AccountResponse>> getAllAccountsById(@PathVariable String id){
        final List<BankAccount> baseEntities = queryDispatcher.send(new FindAccountByIdQuery(id));
        final List<AccountResponse> response = baseEntities.stream().map(AccountResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(params = "accountHolder")
    public ResponseEntity<List<AccountResponse>> getAllAccountsByAccountHolder(@RequestParam(value = "accountHolder") String accountHolder){
        final List<BankAccount> baseEntities = queryDispatcher.send(new FindAccountByAccountHolderQuery(accountHolder));
        final List<AccountResponse> response = baseEntities.stream().map(AccountResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
