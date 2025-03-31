package tproject.tauthservice.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tproject.tauthservice.dto.event.UserRegisteredEvent;

@Service
@RequiredArgsConstructor
public class UserRegisteredConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredConsumer.class);

    @KafkaListener(topics = "user-registered", groupId = "auth-service-group")
    public void consumeUserRegistration(UserRegisteredEvent event) {
        log.info("check thread: {}", Thread.currentThread().getName());
        log.info("Received user registration event: {}", event);
    }
}
