package com.evil.inc.account.query.api.dto;

import com.evil.inc.account.common.dto.Response;
import com.evil.inc.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountResponse extends Response {
    private String id;
    private String accountHolder;
    private String accountType;
    private double balance;

    public static AccountResponse from(BankAccount bankAccount){
        return AccountResponse.builder()
                .id(bankAccount.getId())
                .accountHolder(bankAccount.getAccountHolder())
                .accountType(bankAccount.getAccountType().name())
                .balance(bankAccount.getBalance())
                .build();
    }
}
