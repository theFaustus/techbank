package com.evil.inc.cqrs.core.producers;

import com.evil.inc.cqrs.core.events.Event;

public interface EventProducer {
    void produce(String topic, Event event);
}
