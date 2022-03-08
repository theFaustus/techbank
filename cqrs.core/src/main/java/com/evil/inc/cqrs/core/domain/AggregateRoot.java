package com.evil.inc.cqrs.core.domain;

import com.evil.inc.cqrs.core.events.Event;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class AggregateRoot {
    private final List<Event> changes = new ArrayList<>();
    protected AggregateId id;
    private long version = -1;

    public String getId() {
        return id.toString();
    }

    public AggregateId getAggregateId(){
        return id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<Event> getUncommittedChanges() {
        return List.copyOf(changes);
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(Event event, boolean isNewEvent) {
        try {
            final Method method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            log.warn("The apply method was not found in the aggregate for {}", event.getClass().getName());
        } catch (Exception e) {
            log.error("Error applying event to aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void applyNewChange(Event event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<Event> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
