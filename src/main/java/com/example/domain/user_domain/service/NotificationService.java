package com.example.domain.user_domain.service;

import com.example.fcm.service.dto.MessageRequest;

public interface NotificationService {

    void subscribeOnTopic(Long externalId, String topic);

    void unsubscribeFromTopic(Long externalId, String topic);

    void sendMessageToDevice(Long externalId, String message);

    void publishMessageOnTopic(String topic, String message);

    void sendIdToDevice(Long deviceId, Long id);

    void receiveIdRequest(MessageRequest messageRequest);
}
