package com.evil.inc.cqrs.core.infrastructure;

import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.commands.CommandHandlerConsumer;

public interface CommandDispatcher<T extends Command> {
    void registerHandler(Class<T> type, CommandHandlerConsumer<T> handler);
    void dispatch(T command);
}
