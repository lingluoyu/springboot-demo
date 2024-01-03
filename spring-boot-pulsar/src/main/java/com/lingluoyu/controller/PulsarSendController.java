package com.lingluoyu.controller;

import com.lingluoyu.model.User;
import com.lingluoyu.producer.PulsarProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PulsarSendController {
    @Autowired
    private PulsarProducer producer;

    @PostMapping("send")
    public void sendMessage(@RequestBody User user) {
        try {
            producer.sendMessageToPulsarTopic(user);
        } catch (PulsarClientException e) {
            log.info("pulsar send message error:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
