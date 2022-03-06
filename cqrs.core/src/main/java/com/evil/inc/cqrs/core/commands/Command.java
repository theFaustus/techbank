package com.evil.inc.cqrs.core.commands;

import com.evil.inc.cqrs.core.domain.AggregateId;
import com.evil.inc.cqrs.core.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Command extends Message {
    private LocalDateTime systemCaptureDateTime = LocalDateTime.now();

    public Command(AggregateId id) {
        super(id);
    }

    public String getType() {
        return getClass().getSimpleName();
    }
}
