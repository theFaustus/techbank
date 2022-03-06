package com.evil.inc.account.command.api.commands;

import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.domain.AggregateId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CloseAccountCommand extends Command {
    public CloseAccountCommand(AggregateId id) {
        super(id);
    }
}
