package com.evil.inc.account.command.api.commands;

import com.evil.inc.cqrs.core.commands.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WithdrawFundsCommand extends Command {
    private double amount;
}
