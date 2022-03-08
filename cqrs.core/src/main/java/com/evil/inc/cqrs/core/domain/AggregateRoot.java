package com.evil.inc.cqrs.core.domain;

import com.evil.inc.cqrs.core.events.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class AggregateRoot {
    private final List<Event> changes = new ArrayList<>();
    protected AggregateId id;
    private final AtomicLong version = new AtomicLong();

    public String getId() {
        return id.toString();
    }

    public AggregateId getAggregateId(){
        return id;
    }

    public long getVersion() {
        return version.get();
    }

    public void setVersion(long version) {
        this.version.set(version);
    }

    public List<Event> getUncommittedChanges() {
        return List.copyOf(changes);
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(Event event, boolean isNewEvent) {
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

    public void applyNewChange(Event event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<Event> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
