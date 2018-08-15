package com.example.domain.user_domain.service.impl;

import com.example.domain.user_domain.model.Device;
import com.example.domain.user_domain.model.User;
import com.example.domain.user_domain.service.DeviceService;
import com.example.domain.user_domain.service.NotificationService;
import com.example.domain.user_domain.service.RegisterDeviceService;
import com.example.domain.user_domain.service.UserService;
import com.example.fcm.service.dto.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterDeviceServiceImpl implements RegisterDeviceService {

    private final NotificationService notificationService;

    private final DeviceService deviceService;

    private final UserService userService;

    @Autowired
    public RegisterDeviceServiceImpl(NotificationService notificationService, DeviceService deviceService, UserService userService) {
        this.notificationService = notificationService;
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @Override
    public void processIdRequest(MessageRequest messageRequest) {
        log.info("Processing message.");
        User user = userService.findByLogin(messageRequest.getUserLogin());
        Device device = deviceService.create(messageRequest.getMessageBody(), user);
        notificationService.sendIdToDevice(messageRequest.getDeviceId(), device.getId());
    }

    @Override
    public void sendExternalId(Long forDeviceId, Long externalId) {
        notificationService.sendIdToDevice(forDeviceId, externalId);
    }
}
