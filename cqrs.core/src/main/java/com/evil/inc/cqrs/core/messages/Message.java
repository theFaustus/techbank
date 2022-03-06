package com.evil.inc.cqrs.core.messages;

import com.evil.inc.cqrs.core.domain.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message {
    private AggregateId id;
}
