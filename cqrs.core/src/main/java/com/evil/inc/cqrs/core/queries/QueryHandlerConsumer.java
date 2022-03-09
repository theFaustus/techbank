package com.evil.inc.cqrs.core.queries;

import com.evil.inc.cqrs.core.commands.Command;
import com.evil.inc.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerConsumer<T extends Query, R extends BaseEntity> {
    List<R> accept(T command);
}
