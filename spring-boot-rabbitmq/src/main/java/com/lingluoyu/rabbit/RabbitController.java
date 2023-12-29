package com.lingluoyu.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class RabbitController {
    @Autowired
    private Producer producer;

    @PostMapping("send")
    public void sendMessage(@RequestParam String message) {
        producer.send("hello.exchange", "hello.topic", new Message(message.getBytes(StandardCharsets.UTF_8)));
    }
}
