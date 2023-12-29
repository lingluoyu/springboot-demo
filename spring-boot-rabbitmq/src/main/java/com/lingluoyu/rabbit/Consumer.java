package com.lingluoyu.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = "hello.queue")
    public void receiveMessage(String msg) throws Exception {
        System.out.println("收到消息:" + msg);
    }
}
