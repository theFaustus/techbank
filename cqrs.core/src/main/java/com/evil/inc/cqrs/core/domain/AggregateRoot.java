package com.evil.inc.cqrs.core.domain;

import com.evil.inc.cqrs.core.events.BaseEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class AggregateRoot {
    private final List<BaseEvent> changes = new ArrayList<>();
    protected AggregateId id;
    private final AtomicLong version = new AtomicLong(-1);

    public String getId() {
        return id.toString();
    }

    public long getVersion() {
        return version.get();
    }

    public void setVersion(long version) {
        this.version.set(version);
    }

    public List<BaseEvent> getUncommittedChanges() {
        return List.copyOf(changes);
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent) {
        try {
            getClass().getDeclaredMethod("apply", event.getClass());
        } catch (NoSuchMethodException e) {
            log.warn("The apply method was not found in the aggregate for {}", event.getClass().getName());
        } catch (Exception e) {
            log.error("Error applying event to aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
            version.incrementAndGet();
        }
    }

    public void applyNewChange(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
