package tproject.userservice.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tproject.userservice.dto.event.UserRegisteredEvent;
import tproject.userservice.service.UserPrivacyService;

@Service
@RequiredArgsConstructor
public class UserRegisteredConsumer {

    private final UserPrivacyService userPrivacyService;

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredConsumer.class);

    @KafkaListener(topics = "user-registered", groupId = "user-service-group", concurrency = "3")
    public void consumeUserRegistration(UserRegisteredEvent event) {
        log.info("check thread: {}", Thread.currentThread().getName());
        log.info("Received user registration event: {}", event);
        userPrivacyService.initUserPrivacy(event.getUserId());
    }
}
