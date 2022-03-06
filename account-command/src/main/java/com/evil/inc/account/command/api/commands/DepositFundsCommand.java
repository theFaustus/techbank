package com.evil.inc.account.command.api.commands;

import com.evil.inc.cqrs.core.commands.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepositFundsCommand extends Command {
    private double amount;
}
