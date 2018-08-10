package com.example.domain.user_domain.service;

import com.example.domain.user_domain.service.dto.MessageRequest;
import org.springframework.messaging.MessageHeaders;

public interface RegisterDeviceService {

    void receiveExternalIdRequest(MessageRequest messageRequest, MessageHeaders headers);

    void sendExternalId(Long forDeviceId, Long externalId);

}
