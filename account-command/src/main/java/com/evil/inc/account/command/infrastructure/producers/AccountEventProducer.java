package com.evil.inc.account.command.infrastructure.producers;

import com.evil.inc.cqrs.core.events.Event;
import com.evil.inc.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, Event event) {
        kafkaTemplate.send(topic, event);
    }
}
