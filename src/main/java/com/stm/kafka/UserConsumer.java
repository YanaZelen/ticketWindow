//package com.stm.kafka;
//
//import com.stm.model.User;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserConsumer {
//
//    private final Logger logger = LoggerFactory.getLogger(UserProducer.class);
//
//    @KafkaListener(topics = "${kafka.topic.user}", containerFactory = "userKafkaListenerContainerFactory")
//    public void listenWithHeaders(@Payload User message,
//                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
//                                  @Header(KafkaHeaders.RECEIVED_KEY) String key,
//                                  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                                  @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timeStamp) {
//        logger.info("Received key={} user={ id={} username={} password={} } from topic={} partition={} at timeStamp={}",
//                key, message.getId(), message.getUsername(), message.getPassword(), topic, partition, timeStamp);
//    }
//}
