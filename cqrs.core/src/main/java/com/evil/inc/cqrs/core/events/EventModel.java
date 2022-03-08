package com.evil.inc.cqrs.core.events;

import com.evil.inc.cqrs.core.domain.AggregateId;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collation = "eventStore")
public class EventModel {
    @Id
    private String id;
    private LocalDateTime systemCaptureDateTime;
    private AggregateId aggregateId;
    private String aggregateType;
    @Version
    private long version;
    private String eventType;
    private Event event;
}
