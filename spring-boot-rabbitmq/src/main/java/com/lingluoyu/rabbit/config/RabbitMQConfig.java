package com.lingluoyu.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue helloQueue() {
        return new Queue("hello.queue", true, false, false);
    }

    @Bean
    public TopicExchange helloExchange() {
        return new TopicExchange("hello.exchange", true, false);
    }

    @Bean
    public Binding bindingHelloQueue(Queue helloQueue, TopicExchange helloExchange) {
        return BindingBuilder.bind(helloQueue).to(helloExchange).with("hello.topic");
    }

}
