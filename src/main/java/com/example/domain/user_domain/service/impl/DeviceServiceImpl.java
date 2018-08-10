package com.example.domain.user_domain.service.impl;

import com.example.domain.user_domain.model.Device;
import com.example.domain.user_domain.model.User;
import com.example.domain.user_domain.repository.DeviceRepository;
import com.example.domain.user_domain.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Device create(String descriptions, User user) {
        log.info("Creating new device with description: \'{}\'", descriptions);
        Device device = Device.builder()
                .user(user)
                .description(descriptions)
                .build();
        return repository.save(device);
    }
}