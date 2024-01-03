package com.lingluoyu.consumer;

import com.lingluoyu.constant.Constants;
import com.lingluoyu.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PulsarConsumer {

    @PulsarListener(
            subscriptionName = "string-topic-subscription",
            topics = Constants.STRING_TOPIC,
            subscriptionType = SubscriptionType.Shared
    )
    public void stringTopicListener(String str) {
        log.info("Received String message: {}", str);
    }

    @PulsarListener(
            subscriptionName = "user-topic-subscription",
            topics = Constants.USER_TOPIC,
            subscriptionType = SubscriptionType.Shared,
            schemaType = SchemaType.JSON
    )
//    @PulsarListener(
//            subscriptionName = "user-topic-subscription",
//            topics = USER_TOPIC,
//            subscriptionType = SubscriptionType.Shared,
//            schemaType = SchemaType.JSON,
//            ackMode = AckMode.RECORD,
//            properties = {"ackTimeoutMillis=60000"}
//    )
    public void userTopicListener(User user) {
        log.info("Received user object with email: {}", user.getEmail());
    }


    @PulsarListener(
            subscriptionName = "user-topic-subscription",
            topics = Constants.USER_TOPIC,
            subscriptionType = SubscriptionType.Shared,
            schemaType = SchemaType.JSON,
            deadLetterPolicy = "deadLetterPolicy",
            properties = {"ackTimeoutMillis=60000"}
    )
    public void userTopicListener2(User user) {
        log.info("Second Received user object with email: {}", user.getEmail());
    }

    @PulsarListener(
            subscriptionName = "dead-letter-topic-subscription",
            topics = Constants.USER_DEAD_LETTER_TOPIC,
            subscriptionType = SubscriptionType.Shared
    )
    public void userDlqTopicListener(User user) {
        log.info("Received user object in user-DLQ with email: {}", user.getEmail());
    }
}
