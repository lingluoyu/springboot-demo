package com.lingluoyu.producer;

import com.lingluoyu.model.User;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PulsarProducerTest {

    @Autowired
    private PulsarProducer producer;

    @Test
    void sendMessageToPulsarTopic() {
        User user = new User();
        user.setEmail("782373812@1111.com");
        user.setFirstName("lingluoyu");

        try {
            producer.sendMessageToPulsarTopic(user);
        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }

    }
}