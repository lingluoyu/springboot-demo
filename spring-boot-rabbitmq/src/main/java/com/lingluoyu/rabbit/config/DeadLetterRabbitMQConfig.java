package com.lingluoyu.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : lingluoyu
 * @create 2024/2/20 11:10
 */
@Configuration
public class DeadLetterRabbitMQConfig {

    @Bean
    public DirectExchange ddlDirectExchange() {
        return new DirectExchange("dl_direct_exchange", true, false);
    }

    @Bean
    public Queue ddlQueue() {
        return new Queue("dl.direct.queue", true);
    }

    @Bean
    public Binding ddlBinding() {
        return BindingBuilder.bind(ddlQueue()).to(ddlDirectExchange()).with("dl");
    }
}
