package com.evil.inc.account.command.infrastructure;

import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.commands.CommandHandlerConsumer;
import com.evil.inc.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public final class AccountCommandDispatcher implements CommandDispatcher<Command> {

    private final Map<Class<? extends Command>, CommandHandlerConsumer<Command>> routes = new HashMap<>();

    @Override
    public <C extends Command> void registerHandler(Class<C> type, CommandHandlerConsumer<Command> handler) {
        routes.put(type, handler);
    }

    @Override
    public void dispatch(Command command) {
        var handler = routes.get(command.getClass());
        if(handler == null){
            throw new RuntimeException("No handler registered for " + command.getClass());
        }
        handler.accept(command);
    }
}
