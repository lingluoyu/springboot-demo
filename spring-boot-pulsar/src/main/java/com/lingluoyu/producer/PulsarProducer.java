package com.lingluoyu.producer;

import com.lingluoyu.constant.Constants;
import com.lingluoyu.model.User;
import org.apache.pulsar.client.api.DeadLetterPolicy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;

@Component
public class PulsarProducer {
    @Autowired
    private PulsarTemplate<String> stringTemplate;

    @Autowired
    private PulsarTemplate<User> template;

    public void sendStringMessageToPulsarTopic(String str) throws PulsarClientException {
        stringTemplate.send(Constants.STRING_TOPIC, str);
    }

    public void sendMessageToPulsarTopic(User user) throws PulsarClientException {
        template.send(Constants.USER_TOPIC, user);

//        template.newMessage(user)
//                .withMessageCustomizer(mc -> {
//                    mc.deliverAfter(10L, TimeUnit.SECONDS);
//                })
//                .send();
    }

    @Bean
    DeadLetterPolicy deadLetterPolicy() {
        return DeadLetterPolicy.builder()
                .maxRedeliverCount(2)
                .deadLetterTopic(Constants.USER_DEAD_LETTER_TOPIC)
                .build();
    }
}
