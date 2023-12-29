package com.lingluoyu.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送消息，不需要实现任何接口，供外部调用。
    public void send(String exchange,
                     String routingKey,
                     Message message) {

        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("开始发送消息 : " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationId);
        System.out.println("结束发送消息 : " + message);
    }
}
