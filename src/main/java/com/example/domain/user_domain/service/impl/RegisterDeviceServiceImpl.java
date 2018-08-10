package com.example.domain.user_domain.service.impl;

import com.example.domain.user_domain.model.Device;
import com.example.domain.user_domain.model.User;
import com.example.domain.user_domain.service.DeviceService;
import com.example.domain.user_domain.service.RegisterDeviceService;
import com.example.domain.user_domain.service.UserService;
import com.example.domain.user_domain.service.dto.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterDeviceServiceImpl implements RegisterDeviceService {

    @Value("${spring.kafka.topic.external_id}")
    private String newIdTopic;

    private final KafkaTemplate<String, MessageRequest> template;

    private final DeviceService deviceService;

    private final UserService userservice;

    @Autowired
    public RegisterDeviceServiceImpl(KafkaTemplate<String, MessageRequest> template, DeviceService deviceService, UserService userservice) {
        this.template = template;
        this.deviceService = deviceService;
        this.userservice = userservice;
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic.new_device}")
    public void receiveExternalIdRequest(@Payload MessageRequest messageRequest,
                                         @Headers MessageHeaders headers) {
        log.info("Receive request for external device id. Message request:\n{}", messageRequest);
        User user = userservice.findByLogin(messageRequest.getUserLogin());
        Device device = deviceService.create(messageRequest.getMessageBody(), user);
        sendExternalId(messageRequest.getDeviceId(), device.getId());
    }

    @Override
    public void sendExternalId(Long forDeviceId, Long externalId) {
        MessageRequest messageRequest = MessageRequest.builder()
                .deviceId(forDeviceId)
                .externalId(externalId)
                .build();
        log.info("Sending message on topic: \'{}\', message:\n{}", newIdTopic, messageRequest);
        template.send(newIdTopic, messageRequest);
    }


}
