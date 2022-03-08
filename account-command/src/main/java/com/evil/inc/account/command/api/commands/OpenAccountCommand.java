package com.evil.inc.account.command.api.commands;

import com.evil.inc.account.common.dto.AccountType;
import com.evil.inc.cqrs.core.commands.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OpenAccountCommand extends Command {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
