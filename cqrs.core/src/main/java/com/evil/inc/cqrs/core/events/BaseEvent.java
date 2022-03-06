package com.evil.inc.cqrs.core.events;

import com.evil.inc.cqrs.core.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEvent extends Message {
    private int version;
    private LocalDateTime systemCaptureDateTime = LocalDateTime.now();

    public String getType() {
        return getClass().getSimpleName();
    }
}
