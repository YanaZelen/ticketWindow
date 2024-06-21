package com.stm;

import com.stm.service.KafkaProducerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;


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


