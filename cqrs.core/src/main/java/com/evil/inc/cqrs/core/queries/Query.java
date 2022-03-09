package com.evil.inc.cqrs.core.queries;

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
public abstract class Query  {
    private LocalDateTime systemCaptureDateTime = LocalDateTime.now();

}
