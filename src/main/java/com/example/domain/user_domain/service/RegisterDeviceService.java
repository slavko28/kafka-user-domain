package com.example.domain.user_domain.service;


import com.example.fcm.service.dto.MessageRequest;

public interface RegisterDeviceService {

    void processIdRequest(MessageRequest messageRequest);

    void sendExternalId(Long forDeviceId, Long externalId);

}
