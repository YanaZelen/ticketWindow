package com.stm.service;

import com.stm.kafka.MessageGenerator;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private Producer<String, String> producer;

    @Autowired
    private MessageGenerator messageGenerator;

    private static final String TOPIC_ACCOUNT_FEE = "account-fee-topic";
    private static final String TOPIC_ACCOUNT_OPERATION = "account-operation-topic";
    private static final String TOPIC_DEPOSIT_LOSS = "deposit-loss-topic";
    private static final String TOPIC_DEPOSIT_OPERATION = "deposit-operation-topic";

    public void sendMessages() {
        while (true) {
            try {
//                sendMessageAsync(TOPIC_PROFIT_LOSS, "key1", "some information about profit and loss");
//                sendMessageAsync(TOPIC_DEPOSIT, "key1", "some inf about deposits");

                sendMessageAsync(TOPIC_ACCOUNT_FEE, "key1", messageGenerator.generateAccountFeeMessage());
                sendMessageAsync(TOPIC_ACCOUNT_OPERATION, "key1", messageGenerator.generateAccountOperationMessage());
                sendMessageAsync(TOPIC_DEPOSIT_LOSS, "key1", messageGenerator.generateDepositLossMessage());
                sendMessageAsync(TOPIC_DEPOSIT_OPERATION, "key1", messageGenerator.generateDepositOperationMessage());
            } catch (Exception e) {
                logger.error("Error sending messages: {}", e.getMessage());
            }

            // Wait for 60 seconds before the next iteration
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sendMessageAsync(final String topic, final String key, final String message) throws Exception {

        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record, (metadata, exception) -> {
            long elapsedTime = System.currentTimeMillis() - System.currentTimeMillis();
            if (metadata != null) {
                logger.info("sent record(key={} value={}) meta(partition={}, offset={}) time={}",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);
            } else {
                exception.printStackTrace();
            }
        });
    }

    private void sendMessageSync(final String topic, final String key, final String message) throws Exception {

        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        RecordMetadata metadata = producer.send(record).get();
        long elapsedTime = System.currentTimeMillis() - System.currentTimeMillis();
        logger.info("sent record(key={} value={}) meta(partition={}, offset={}) time={}",
                record.key(), record.value(), metadata.partition(),
                metadata.offset(), elapsedTime);
    }
}