package tproject.tauthservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import tproject.tauthservice.dto.event.UserRegisteredEvent;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserRegisteredProducer {

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredProducer.class);
    private static final String USER_REGISTERED_TOPIC = "user-registered";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserRegistrationEvent(UserRegisteredEvent event) {
        log.info("Sending user registration event to Kafka: {}", event);

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(USER_REGISTERED_TOPIC, event.getUserId().toString(), event);

        future.whenComplete((result, ex) -> {

            log.info("check thread kafka: {}", Thread.currentThread().getName());
            if (ex != null) {
                log.error("Unable to send user registration event to Kafka: {}", ex.getMessage());
            } else {
                log.info("User registration event sent successfully to topic: {}, partition: {}, offset: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
        log.info("check thread: {}", Thread.currentThread().getName());

    }
}