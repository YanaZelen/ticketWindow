package com.stm;

import com.stm.model.Role;
import com.stm.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class})
public class App {

    private final static String TOPIC = "test-topic-14";
    private final static String CLIENT_ID = "test-service-group-id";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final Logger logger = LoggerFactory.getLogger("App");

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);

        User user1 = new User(1L, "username1", "password1", Role.ROLE_USER);
        String json1 = JsonUtil.toJson(user1);
        sendMessageAsync("user-1", json1);

        User user2 = new User(2L, "username2", "password2", Role.ROLE_USER);
        String json2 = JsonUtil.toJson(user2);
        sendMessageSync("user-2", json2);
    }

    private static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    static void sendMessageSync(final String key, final String message) throws Exception {
        final Producer<String, String> producer = createProducer();
        long time = System.currentTimeMillis();

        try {
            final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, key, message);
            RecordMetadata metadata = producer.send(record).get();

            long elapsedTime = System.currentTimeMillis() - time;

            logger.info("sent record(key={} value={}) meta(partition={}, offset={}) time={}",
                    record.key(), record.value(), metadata.partition(),
                    metadata.offset(), elapsedTime);

        } finally {
            producer.flush();
            producer.close();
        }
    }

    static void sendMessageAsync(final String key, final String message) throws Exception {
        final Producer<String, String> producer = createProducer();
        long time = System.currentTimeMillis();

        try {
            final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, key, message);

            producer.send(record, (metadata, exception) -> {
                long elapsedTime = System.currentTimeMillis() - time;
                if (metadata != null) {
                    logger.info("sent record(key={} value={}) meta(partition={}, offset={}) time={}",
                            record.key(), record.value(), metadata.partition(),
                            metadata.offset(), elapsedTime);
                } else {
                    exception.printStackTrace();
                }
            });

        } finally {
            producer.flush();
            producer.close();
        }
    }

}


