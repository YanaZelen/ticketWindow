package com.stm.kafka;

import com.stm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
public class CarDtoProducer {

    private final Logger logger = LoggerFactory.getLogger(CarDtoProducer.class);

    @Value(value = "${kafka.topic.user}")
    private String carDtoTopic;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User user) {
        ListenableFuture<SendResult<String, User>> future = (ListenableFuture<SendResult<String, User>>) kafkaTemplate.send(carDtoTopic, generateUserKey(), user);

        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
            @Override
            public void onSuccess(SendResult<String, User> result) {
                logger.info("Sent message={} with offset=={}", user, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable exception) {
                logger.info("Unable to send message={} due to : {}", user, exception.getMessage());
            }
        });
    }

    private String generateUserKey() {
        return "User-" + UUID.randomUUID();
    }
}
