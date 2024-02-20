package com.lingluoyu.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Author : lingluoyu
 * @create 2024/2/20 11:16
 */
@Configuration
public class TTLRabbitMQConfig {
    @Bean
    public DirectExchange ttlDirectExchange() {
        return new DirectExchange("ttl_direct_exchange", true, false);
    }

    /**
     * 进入死信队列条件（可同时共存）
     *  1. 消息过期
     *  2. 队列消息条数超过最大条数
     * @return
     */
    @Bean
    public Queue ttlQueue() {
        HashMap<Object, Object> args = new HashMap<>();
        // 设置队列过期时间
        args.put("x-message-ttl", 5000);
        // 设置队列最大长度
        args.put("x-max-length", 8);
        // 设置绑定交换机
        args.put("x-dead-letter-exchange", "dl_direct_exchange");
        // 设置死信队列路由 key 若是 fanout 模式则不需要配
        args.put("x-dead-letter-routing-key", "dl");
        return new Queue("ttl.direct.queue", true);
    }

    @Bean
    public Binding ddlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with("ttl");
    }
}
