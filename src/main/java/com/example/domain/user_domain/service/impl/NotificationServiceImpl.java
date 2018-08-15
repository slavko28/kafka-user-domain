package com.example.domain.user_domain.service.impl;

import com.example.domain.user_domain.service.NotificationService;
import com.example.domain.user_domain.service.RegisterDeviceService;
import com.example.fcm.service.dto.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${spring.kafka.topic.messaging}")
    private String messagingTopic;

    @Value("${spring.kafka.topic.external_id}")
    private String newIdTopic;

    private final KafkaTemplate<String, MessageRequest> template;

    private final RegisterDeviceService registerDeviceService;

    @Autowired
    public NotificationServiceImpl(KafkaTemplate<String, MessageRequest> template,@Lazy RegisterDeviceService registerDeviceService) {
        this.template = template;
        this.registerDeviceService = registerDeviceService;
    }

    @Override
    public void subscribeOnTopic(Long externalId, String topic) {
        MessageRequest messageRequest = MessageRequestUtil.createRequestForSubscribeDevice(externalId, topic);
        sendMessageOnTopic(messagingTopic, messageRequest);
    }

    @Override
    public void unsubscribeFromTopic(Long externalId, String topic) {
        MessageRequest messageRequest = MessageRequestUtil.createRequestForUnsubscribeDevice(externalId, topic);
        sendMessageOnTopic(messagingTopic, messageRequest);
    }

    @Override
    public void sendMessageToDevice(Long externalId, String message) {
        MessageRequest messageRequest = MessageRequestUtil.createRequestForMessagingToDevice(externalId, message);
        sendMessageOnTopic(messagingTopic, messageRequest);
    }

    @Override
    public void publishMessageOnTopic(String topic, String message) {
        MessageRequest messageRequest = MessageRequestUtil.createRequestForPublishMessageOnTopic(topic, message);
        sendMessageOnTopic(messagingTopic, messageRequest);
    }

    @Override
    public void sendIdToDevice(Long deviceId, Long id) {
        MessageRequest messageRequest = MessageRequestUtil.createRequestForNewDevice(deviceId, id);
        sendMessageOnTopic(newIdTopic, messageRequest);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic.new_device}")
    public void receiveIdRequest(@Payload MessageRequest messageRequest) {
        registerDeviceService.processIdRequest(messageRequest);
    }

    private void sendMessageOnTopic(String topic, MessageRequest messageRequest) {
        log.info("Publish on topic: {}.\nMessage request: {}", topic, messageRequest);
        template.send(topic, messageRequest);
    }

}
