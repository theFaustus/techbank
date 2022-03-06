package com.evil.inc.cqrs.core.commands;

@FunctionalInterface
public interface CommandHandlerConsumer<T extends Command> {
    void accept(T command);
}
