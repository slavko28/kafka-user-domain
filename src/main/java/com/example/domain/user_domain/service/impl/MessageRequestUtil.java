package com.example.domain.user_domain.service.impl;

import com.example.fcm.model.enums.NotificationAction;
import com.example.fcm.model.enums.NotificationType;
import com.example.fcm.service.dto.MessageRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestUtil {

    public static MessageRequest createRequestForSubscribeDevice(Long id, String topic) {
        log.info("Create message request to subscribe device with id: {} on topic: {}", id, topic);
        return MessageRequest.builder()
                .externalId(id)
                .topicName(topic)
                .notificationType(NotificationType.topic)
                .notificationAction(NotificationAction.subscribe)
                .build();
    }

    public static MessageRequest createRequestForUnsubscribeDevice(Long id, String topic) {
        log.info("Create message request to unsubscribe device with id: {} on topic: {}", id, topic);
        return MessageRequest.builder()
                .externalId(id)
                .topicName(topic)
                .notificationType(NotificationType.topic)
                .notificationAction(NotificationAction.unsubscribe)
                .build();
    }

    public static MessageRequest createRequestForMessagingToDevice(Long id, String message) {
        log.info("Create message request to send message on device with id: {} message: {}", id, message);
        return MessageRequest.builder()
                .externalId(id)
                .messageBody(message)
                .notificationType(NotificationType.single)
                .notificationAction(NotificationAction.publish)
                .build();
    }

    public static MessageRequest createRequestForPublishMessageOnTopic(String topic, String message) {
        log.info("Create message request to publish message on topic: {} message: {}", topic, message);
        return MessageRequest.builder()
                .topicName(topic)
                .messageBody(message)
                .notificationType(NotificationType.topic)
                .notificationAction(NotificationAction.publish)
                .build();
    }

    public static MessageRequest createRequestForNewDevice(Long deviceId, Long id) {
        log.info("Create message request to send assigned device external id : {}", id);
        return MessageRequest.builder()
                .deviceId(deviceId)
                .externalId(id)
                .build();
    }
}
