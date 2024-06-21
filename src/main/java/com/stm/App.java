package com.stm;

import com.stm.model.Role;
import com.stm.model.User;
import com.stm.service.KafkaProducerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void init() {
        kafkaProducerService.sendMessages();
    }
}


